package com.zhangqi.percentagebar;

/**
 * Created by whx on 2016/12/2.
 */

public class BarChart {
    int data_id;
    String week;
    int PNColor;//0=正 1=负
    float num;
    Coordinate coordinate;

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getPNColor() {
        return PNColor;
    }

    public void setPNColor(int PNColor) {
        this.PNColor = PNColor;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    public static class Coordinate {
        float leftX;
        float topY;
        float rightX;
        float bottomY;

        public float getLeftX() {
            return leftX;
        }

        public void setLeftX(float leftX) {
            this.leftX = leftX;
        }

        public float getTopY() {
            return topY;
        }

        public void setTopY(float topY) {
            this.topY = topY;
        }

        public float getRightX() {
            return rightX;
        }

        public void setRightX(float rightX) {
            this.rightX = rightX;
        }

        public float getBottomY() {
            return bottomY;
        }

        public void setBottomY(float bottomY) {
            this.bottomY = bottomY;
        }
    }
}
