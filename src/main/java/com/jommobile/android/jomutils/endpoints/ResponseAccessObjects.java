package com.jommobile.android.jomutils.endpoints;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.jommobile.android.jomutils.db.BaseEntity;
import com.jommobile.android.jomutils.model.BaseModel;
import com.jommobile.android.jomutils.model.Flavor;
import com.jommobile.android.jomutils.model.Image;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static Image createImageModel(Object imageResp) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> imageRespClass = imageResp.getClass();
        Image img = new Image();
        img.setImage((String) imageRespClass.getMethod("getLink").invoke(imageResp));
        img.setThumbnail((String) imageRespClass.getMethod("getThumbnail").invoke(imageResp));

        return img;
    }

    public static Object createInnerModel(Class<?> innerModelClass, Object innerRespObj) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> innerRespClass = innerRespObj.getClass();

        Object innerModelObj = innerModelClass.newInstance();

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

    public static <M> M createImageResponse(Class<M> imgResponseClass, Image imageModel) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        M image = imgResponseClass.newInstance();
        imgResponseClass.getMethod("setLink", String.class).invoke(image, imageModel.getImage());
        imgResponseClass.getMethod("setThumbnail", String.class).invoke(image, imageModel.getThumbnail());

        return image;
    }

    @SuppressWarnings("unchecked")
    public static <R extends GenericJson, M extends BaseEntity> M createModel(Class<M> modelClass, R resp, Class<?>... innerTypes) {
        final Class<R> rClass = (Class<R>) resp.getClass();

        M model = null;
        try {
            model = modelClass.newInstance();

            Long id = (Long) rClass.getMethod("getId").invoke(resp);
            DateTime createdDate = (DateTime) rClass.getMethod("getCreatedDate").invoke(resp);
            DateTime modifiedDate = (DateTime) rClass.getMethod("getModifiedDate").invoke(resp);
            String flavor = (String) rClass.getMethod("getFlavor").invoke(resp);

            model.setId(id != null ? id : 0);
            model.setCreatedDate(new Date(createdDate.getValue()));
            model.setModifiedDate(new Date(modifiedDate.getValue()));
            model.setFlavor(Flavor.of(flavor));
            model.setSync(true);

        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (model != null) {
            Method[] methods = modelClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                String name = method.getName();
                if (name.startsWith("set")) {
                    if (!name.equals("setId") && !name.equals("setCreatedDate")
                            && !name.equals("setModifiedDate") && !name.equals("setFlavor")
                            && !name.equals("setSync")) {

                        final String fieldName = name.substring(3);

                        try {
                            Object rField = rClass.getMethod("get" + fieldName).invoke(resp);

                            if (fieldName.equals("Image")) {
                                method.invoke(model, createImageModel(rField));
                            } else if (rField instanceof DateTime) {
                                method.invoke(model, DateTimeConverter.toDate((DateTime) rField));
                            } else if (!(rField instanceof String) && !(rField instanceof Number)) {
                                Class<?> innerModelType = null;
                                if (innerTypes != null && innerTypes.length > 0) {
                                    for (Class<?> innerType : innerTypes) {
                                        if (innerType.getSimpleName().equals(fieldName)) {
                                            innerModelType = innerType;
                                        }
                                    }
                                }

                                method.invoke(model, innerModelType != null ? createInnerModel(innerModelType, rField) : null);
                            } else {
                                // else if (!(rField instanceof String) && !(rField instanceof Number))
                                // try to cast other object that you may know from server and local model
                                method.invoke(model, rField);
                            }

                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException | InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        return model;
    }

    @SuppressWarnings("unchecked")
    public static <R extends GenericJson, M extends BaseModel> R createResponse(Class<R> responseClass, M model, Class<?>... innerTypes) {
        // TODO: 1/11/19 need to be tested
        final Class<M> mClass = (Class<M>) model.getClass();

        R response = null;
        try {
            response = responseClass.newInstance();

            responseClass.getMethod("setId", Long.class).invoke(response, model.getId());
            responseClass.getMethod("setCreatedDate", DateTime.class).invoke(response,
                    model.getCreatedDate() != null ? new DateTime(model.getCreatedDate().getTime()) : (DateTime) null);
            responseClass.getMethod("setModifiedDate", DateTime.class).invoke(response,
                    model.getModifiedDate() != null ? new DateTime(model.getModifiedDate().getTime()) : (DateTime) null);

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

    @SuppressWarnings("unchecked")
    public static <R extends GenericJson, M extends BaseEntity> List<M> createModelList(Class<M> modelClass, List<R> responses) {
        List<M> modelList = new ArrayList<>();

        for (R response : responses) {
            modelList.add(createModel(modelClass, response));
        }

        return modelList;
    }

}
