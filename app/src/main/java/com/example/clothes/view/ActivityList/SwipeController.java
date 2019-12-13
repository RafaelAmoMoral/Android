package com.example.clothes.view.ActivityList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.ItemTouchHelper;

import androidx.recyclerview.widget.RecyclerView;


import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;

class SwipeController extends ItemTouchHelper.Callback {

    private boolean swipeBack;
    enum ButtonsState {
        GONE,
        LEFT_VISIBLE,
        RIGHT_VISIBLE
    }
    private ButtonsState buttonShowedState = ButtonsState.GONE;
    private static final float buttonWidth = 300;
    private ActivityList parent;

    public SwipeController(Activity parent) {
        this.swipeBack = false;
        this.parent=(ActivityList) parent;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = false;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c,
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        if (actionState == ACTION_STATE_SWIPE) {
            setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                                  final float dX, final float  dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int itemSwipped = viewHolder.getAdapterPosition();

                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if (swipeBack) {
                    if (dX < -buttonWidth){
                        parent.getPresenter().onClotheSwipped(itemSwipped);
                        //createSimpleDialog(itemSwipped).show();
                        buttonShowedState = ButtonsState.RIGHT_VISIBLE;
                    }
                    else if (dX > buttonWidth){
                        buttonShowedState  = ButtonsState.LEFT_VISIBLE;
                    }

                    if (buttonShowedState != ButtonsState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c,
                                      final RecyclerView recyclerView,
                                      final RecyclerView.ViewHolder viewHolder,
                                      final float dX, final float dY,
                                      final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c,
                                    final RecyclerView recyclerView,
                                    final RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY,
                                    final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;
                    buttonShowedState = ButtonsState.GONE;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView,
                                   boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    /*public AlertDialog createSimpleDialog(final int index) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);

        builder.setTitle("¿Está seguro de que desea eliminar?")
                .setMessage("Recuerde que esta decisión será permanente.")
                .setPositiveButton("Eliminar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               parent.removeItemInList(index);
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        dialog = builder.create();
        return dialog;
    }*/


}