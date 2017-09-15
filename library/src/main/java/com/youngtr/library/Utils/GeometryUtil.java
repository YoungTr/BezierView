package com.youngtr.library.Utils;

import android.graphics.PointF;

/**
 * Created by YoungTr on 2017/9/14.
 */

public class GeometryUtil {

    private static final double NEGATIVE_NOE = -1.0d;

    /**
     * 获得两点之间的距离
     *
     * @param p0
     * @param p1
     * @return
     */
    public static float getDistanceBetween2Points(PointF p0, PointF p1) {
        return (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) + Math.pow(p0.x - p1.x, 2));
    }

    /**
     * 获得两点连线的中点
     *
     * @param p1
     * @param p2
     * @return
     */
    public static PointF getMiddlePoint(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
    }

    /**
     * Get point between p1 and p2 by percent.
     * 根据百分比获取两点之间的某个点坐标
     *
     * @param p1
     * @param p2
     * @param percent
     * @return
     */
    public static PointF getPointByPercent(PointF p1, PointF p2, float percent) {
        return new PointF(evaluateValue(percent, p1.x, p2.x), evaluateValue(percent, p1.y, p2.y));
    }

    /**
     * 根据分度值，计算从start到end中，fraction位置的值。fraction范围为0 -> 1
     *
     * @param fraction
     * @param start
     * @param end
     * @return
     */
    public static float evaluateValue(float fraction, Number start, Number end) {
        return start.floatValue() + (end.floatValue() - start.floatValue()) * fraction;
    }

    /**
     * Get the point of intersection between circle and line.
     * 获取 通过指定圆心，斜率为lineK的直线与圆的交点。
     *
     * @param circleCenter The circle center point.
     * @param radius       The circle radius.
     * @param lineK        The slope of line which cross the pMiddle.
     * @return
     */
    public static PointF[] getIntersectionPoints(PointF circleCenter, float radius, Double lineK) {
        PointF[] points = new PointF[2];

        float radian, xOffset, yOffset;
        if (lineK != null) {

            radian = (float) Math.atan(lineK);
            xOffset = (float) (Math.cos(radian) * radius);
            yOffset = (float) (Math.sin(radian) * radius);
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new PointF(circleCenter.x + xOffset, circleCenter.y + yOffset);
        points[1] = new PointF(circleCenter.x - xOffset, circleCenter.y - yOffset);

        return points;
    }

    /**
     * 计算两点之间的斜率
     *
     * @param p1
     * @param p2
     * @return
     */
    public static float caculateSlope(PointF p1, PointF p2) {
        return (p1.y - p2.y) / (p1.x - p2.x);
    }

    /**
     * 计算两点之间的斜率的
     *
     * @param p1
     * @param p2
     * @return
     */
    public static double caculateAntiSlope(PointF p1, PointF p2) {
        return NEGATIVE_NOE / caculateSlope(p1, p2);
    }

    public static PointF[] getCutPointF(PointF circleCenter, PointF controlPoint, float radius) {
        PointF[] pointFs = new PointF[2];
        float distanceToFix = GeometryUtil.getDistanceBetween2Points(circleCenter, controlPoint);
        double acosFix = Math.acos(radius / distanceToFix);
        double acosSlope = Math.acos((controlPoint.x - circleCenter.x) / distanceToFix);
        float offsetX1 = (float) (radius * Math.cos(acosFix - acosSlope));
        float offsetY1 = (float) (radius * Math.sin(acosFix - acosSlope));
        double c = Math.acos((controlPoint.y - circleCenter.y) / distanceToFix);
        float offsetX2 = (float) (radius * Math.sin(acosFix - c));
        float offsetY2 = (float) (radius * Math.cos(acosFix - c));
        pointFs[0] = new PointF(circleCenter.x + offsetX1, circleCenter.y - offsetY1);
        pointFs[1] = new PointF(circleCenter.x - offsetX2, circleCenter.y + offsetY2);
        return pointFs;
    }

}
