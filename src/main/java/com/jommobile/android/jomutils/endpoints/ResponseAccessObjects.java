package com.jommobile.android.jomutils.endpoints;

import androidx.annotation.NonNull;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.jommobile.android.jomutils.db.BaseEntity;
import com.jommobile.android.jomutils.db.FlavorConverter;
import com.jommobile.android.jomutils.model.BaseModel;
import com.jommobile.android.jomutils.model.Image;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.google.api.client.util.DateTime;

/**
 * Response Access Object class to map, transform or create between
 * Endpoints' response and model.
 *
 * @author MAO Hieng on 1/10/19.
 */
public final class ResponseAccessObjects {

    private ResponseAccessObjects() {
        //no instance
    }

    private static Image createImageModel(@NonNull Object imageResp) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> imageRespClass = imageResp.getClass();
        Image img = new Image();
        img.setImage((String) imageRespClass.getMethod("getLink").invoke(imageResp));
        img.setThumbnail((String) imageRespClass.getMethod("getThumbnail").invoke(imageResp));

        return img;
    }

    private static Object createInnerClientObject(Class<?> innerModelClass, Object innerRespObj) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> innerRespClass = innerRespObj.getClass();

        Object innerModelObj = innerModelClass.newInstance();
        // TODO: 7/19/2019 this will fail the whole if there's a unmatched field
        Method[] innerObjMethods = innerModelObj.getClass().getMethods();
        for (Method innerObjMethod : innerObjMethods) {
            String methodName = innerObjMethod.getName();
            if (methodName.startsWith("set")) {
                String setMethodName = methodName.substring(3);

                Object innerRespObjField = innerRespClass.getMethod("get" + setMethodName).invoke(innerRespObj);
                innerObjMethod.invoke(innerModelObj, innerRespObjField);
            }
        }

        return innerModelObj;
    }

    @Deprecated
    public static Object createInnerModel(Class<?> innerModelClass, Object innerRespObj) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return createInnerClientObject(innerModelClass, innerRespObj);
    }

    private static <M> M createImageResponse(Class<M> imgResponseClass, Image imageModel) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        M image = imgResponseClass.newInstance();
        imgResponseClass.getMethod("setLink", String.class).invoke(image, imageModel.getImage());
        imgResponseClass.getMethod("setThumbnail", String.class).invoke(image, imageModel.getThumbnail());

        return image;
    }

    // TODO: 6/10/2019 change method name to createClientModel

    @SuppressWarnings("unchecked")
    static <R extends GenericJson, M extends BaseEntity> M createClientObject(Class<M> modelClass, R resp, Class<?>... innerTypes) {
        final Class<R> rClass = (Class<R>) resp.getClass();

        M model = null;
        try {
            model = modelClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException("Class " + modelClass.getSimpleName() + " must have a default constructor.");
        }

        // Required fields
        try {
            Long id = (Long) rClass.getMethod("getId").invoke(resp);

            Object created = rClass.getMethod("getCreatedDate").invoke(resp);
            Object modified = rClass.getMethod("getModifiedDate").invoke(resp);
            Date createDate = null;
            Date modifiedDate = null;

            if (created instanceof DateTime) {
                createDate = DateTimeConverter.toDate((DateTime) created);
                modifiedDate = DateTimeConverter.toDate((DateTime) modified);
            } else if (created instanceof String) {
                String createdStr = (String) created;
                if (createdStr.contains("-")) {
                    createDate = DateTimeConverter.toDate(createdStr);
                    modifiedDate = DateTimeConverter.toDate((String) modified);
                } else {
                    createDate = DateTimeConverter.toDate(Long.parseLong(createdStr));
                    modifiedDate = DateTimeConverter.toDate(Long.parseLong((String) modified));
                }
            } else {
                if (created != null) {
                    try {
                        createDate = DateTimeConverter.toDate((Long) created);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }

                if (modified != null) {
                    try {
                        modifiedDate = DateTimeConverter.toDate((Long) modified);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
            }

            model.setId(id != null ? id : 0);
            model.setCreatedDate(createDate);
            model.setModifiedDate(modifiedDate);
            model.setSync(true);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // Note: trying to match any fields, need to catch exceptions for each of them.

        // Optional fields
        String flavor = null;
        try {
            flavor = (String) rClass.getMethod("getFlavor").invoke(resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        model.setFlavor(FlavorConverter.toFlavor(flavor));

        boolean deleted = false;
        try {
            deleted = (Boolean) rClass.getMethod("isDeleted").invoke(resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            try {
                deleted = (Boolean) rClass.getMethod("getDeleted").invoke(resp);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                try {
                    deleted = !((Boolean) rClass.getMethod("getActive").invoke(resp));
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                } catch (InvocationTargetException e2) {
                    e2.printStackTrace();
                } catch (NoSuchMethodException e2) {
                    try {
                        deleted = !((Boolean) rClass.getMethod("isActive").invoke(resp));
                    } catch (IllegalAccessException e3) {
                        e3.printStackTrace();
                    } catch (InvocationTargetException e3) {
                        e3.printStackTrace();
                    } catch (NoSuchMethodException e3) {
                        e3.printStackTrace();
                    }
                }
            }
        }

        model.setDeleted(deleted);

        // Other fields that matchable
        Method[] methods = modelClass.getMethods();
        for (Method setMethod : methods) {
            String name = setMethod.getName();
            if (name.startsWith("set")) {
                boolean skipCheck = name.equals("setId")
                        || name.equals("setCreatedDate")
                        || name.equals("setModifiedDate")
                        || name.equals("setFlavor")
                        || name.equals("setSync")
                        || name.equals("setDeleted");

                if (!skipCheck) {
                    final String firstCapFieldName = name.substring(3);

                    try {
                        Object respFieldValue = rClass.getMethod("get" + firstCapFieldName).invoke(resp);

                        if (respFieldValue != null) {
                            if (firstCapFieldName.equals("Image")) {
                                setMethod.invoke(model, createImageModel(respFieldValue));
                            } else if (respFieldValue instanceof DateTime) {
                                setMethod.invoke(model, DateTimeConverter.toDate((DateTime) respFieldValue));
                            } else if ((respFieldValue instanceof String) || (respFieldValue instanceof Number)) {
                                // else if (!(respFieldValue instanceof String) && !(respFieldValue instanceof Number))
                                // try to cast other object that you may know from server and local model
                                setMethod.invoke(model, respFieldValue);
                            } else {
                                Object innerObject = null;
                                if (innerTypes != null && innerTypes.length > 0) {
                                    Class<?> innerModelType = null;
                                    // Find inner class type if provided
                                    for (Class<?> innerType : innerTypes) {
                                        if (innerType.getSimpleName().equals(firstCapFieldName)) {
                                            innerModelType = innerType;
                                            break;
                                        }
                                    }

                                    // Get an instance
                                    if (innerModelType != null) {
                                        innerObject = createInnerClientObject(innerModelType, respFieldValue);
                                    }
                                }

                                setMethod.invoke(model, innerObject == null ? respFieldValue : innerObject);
                            }
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return model;
    }

    @Deprecated
    public static <R extends GenericJson, M extends BaseEntity> M createModel(Class<M> modelClass, R resp, Class<?>... innerTypes) {
        return createClientObject(modelClass, resp, innerTypes);
    }

    @SuppressWarnings("unchecked")
    static <R extends GenericJson, M extends BaseModel> R createResponse(Class<R> responseClass, M model, Class<?>... innerTypes) {
        // TODO: 1/11/19 need to be tested
        final Class<M> mClass = (Class<M>) model.getClass();

        R response = null;
        try {
            response = responseClass.newInstance();

            responseClass.getMethod("setId", Long.class).invoke(response, model.getId());

            Date created = model.getCreatedDate();
            responseClass.getMethod("setCreatedDate", DateTime.class).invoke(response,
                    created != null ? DateTimeConverter.toDateTime(created) : (DateTime) null);
            Date modified = model.getModifiedDate();
            responseClass.getMethod("setModifiedDate", DateTime.class).invoke(response,
                    modified != null ? DateTimeConverter.toDateTime(modified) : (DateTime) null);

        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (response != null) {
            Class<?> imageResponseClass = null;
            if (innerTypes != null && innerTypes.length > 0) {
                for (Class<?> innerType : innerTypes) {
                    if (innerType.getSimpleName().equals("Image")) {
                        imageResponseClass = innerType;
                    }
                }
            }

            Method[] methods = responseClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                String name = method.getName();
                if (name.startsWith("set")) {
                    if (!name.equals("setId") && !name.equals("setCreatedDate")
                            && !name.equals("setModifiedDate")) {
                        String fieldName = name.substring(3);

                        try {
                            Object dataField = mClass.getMethod("get" + fieldName).invoke(model);
                            if (dataField instanceof Image && imageResponseClass != null) {
                                method.invoke(response, createImageResponse(imageResponseClass, (Image) dataField));
                            }
                            // TODO: 1/13/19 cast Date to DateTime type, and others...
                            else {
                                // else if (!(rField instanceof String) && !(rField instanceof Number))
                                // try to cast other object that you may know from server and local model
                                method.invoke(response, dataField);
                            }
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException | InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return response;
    }

    // TODO: 6/10/2019 remove old methods

    @Deprecated
    public static <R extends GenericJson, M extends BaseEntity> List<M> createModelList(Class<M> modelClass, List<R> responses) {
        return createClientObjectList(modelClass, responses);
    }

    static <R extends GenericJson, M extends BaseEntity> List<M> createClientObjectList(Class<M> modelClass, List<R> responses) {
        List<M> modelList = new ArrayList<>();

        for (R response : responses) {
            modelList.add(createClientObject(modelClass, response));
        }

        return modelList;
    }

}
