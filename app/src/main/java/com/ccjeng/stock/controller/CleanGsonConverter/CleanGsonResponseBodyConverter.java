package com.ccjeng.stock.controller.CleanGsonConverter;

import android.util.Log;

import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by andycheng on 2016/3/17.
 */
public class CleanGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    CleanGsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        try {

            String s = value.string().replace("// ",""); //.replace("[","").replace("]","");

            System.out.println(s);

            return adapter.fromJson(s);
            //return adapter.fromJson(value.charStream());

        } finally {
            value.close();
        }
    }
}
