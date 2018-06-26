package io.exera.quotz.tools.swipedeck.Utility;

import android.view.View;

/**
 * Created by aaron on 10/08/2016.
 */
public interface SwipeCallback {
    void cardSwipedLeft(View card);
    void cardSwipedRight(View card);
    void cardOffScreen(View card);
    void cardActionDown();
    void cardActionUp();

    /**
     * Check whether we can start dragging current view.
     * @return true if we can start dragging view, false otherwise
     */
    boolean isDragEnabled();
}
