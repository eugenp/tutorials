package com.baeldung.ini;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public class MyConfiguration {

    @JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
    public static class Fonts {
        private String letter;
        private int textSize;

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }
    }

    public static class Background {
        private String color;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    @JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
    public static class RequestResult {
        private int requestCode;

        public int getRequestCode() {
            return requestCode;
        }

        public void setRequestCode(int requestCode) {
            this.requestCode = requestCode;
        }
    }

    @JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
    public static class ResponseResult {
        private int resultCode;

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }
    }

    private Fonts fonts;
    private Background background;

    @JsonProperty("RequestResult")
    private RequestResult requestResult;

    @JsonProperty("ResponseResult")
    private ResponseResult responseResult;

    public Fonts getFonts() {
        return fonts;
    }

    public void setFonts(Fonts fonts) {
        this.fonts = fonts;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public RequestResult getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(RequestResult requestResult) {
        this.requestResult = requestResult;
    }

    public ResponseResult getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(ResponseResult responseResult) {
        this.responseResult = responseResult;
    }
}
