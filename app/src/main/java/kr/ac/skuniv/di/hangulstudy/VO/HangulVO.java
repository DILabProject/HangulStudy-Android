package kr.ac.skuniv.di.hangulstudy.VO;

import com.google.gson.JsonArray;

import java.util.List;

/**
 * Created by Juhyun on 2018-02-12.
 */

public class HangulVO {
    JsonArray word;
    List<String> stroke;

    public JsonArray getWord() {
        return word;
    }
    public void setWord(JsonArray word) {
        this.word = word;
    }
    public List<String> getStroke() {
        return stroke;
    }
    public void setStroke(List<String> stroke) {
        this.stroke = stroke;
    }
}