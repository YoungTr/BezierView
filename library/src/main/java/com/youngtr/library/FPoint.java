package com.youngtr.library;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/9/12.
 */

public class FPoint implements Parcelable {
    public float x;
    public float y;

    public FPoint() {
    }

    public FPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public FPoint(FPoint src) {
        this.x = src.x;
        this.y = src.y;
    }

    /**
     * Set the pofloat's x and y coordinates
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Negate the pofloat's coordinates
     */
    public final void negate() {
        x = -x;
        y = -y;
    }

    /**
     * Offset the pofloat's coordinates by dx, dy
     */
    public final void offset(float dx, float dy) {
        x += dx;
        y += dy;
    }

    /**
     * Returns true if the pofloat's coordinates equal (x,y)
     */
    public final boolean equals(float x, float y) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FPoint pofloat = (FPoint) o;

        if (x != pofloat.x) return false;
        if (y != pofloat.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        float result = x;
        result = 31 * result + y;
        return (int) result;
    }

    @Override
    public String toString() {
        return "FPoint(" + x + ", " + y + ")";
    }

    /**
     * @hide
     */
    public void prfloatShortString(PrintWriter pw) {
        pw.print("[");
        pw.print(x);
        pw.print(",");
        pw.print(y);
        pw.print("]");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.x);
        dest.writeFloat(this.y);
    }

    protected FPoint(Parcel in) {
        this.x = in.readFloat();
        this.y = in.readFloat();
    }

    public static final Parcelable.Creator<FPoint> CREATOR = new Parcelable.Creator<FPoint>() {
        @Override
        public FPoint createFromParcel(Parcel source) {
            return new FPoint(source);
        }

        @Override
        public FPoint[] newArray(int size) {
            return new FPoint[size];
        }
    };
}
