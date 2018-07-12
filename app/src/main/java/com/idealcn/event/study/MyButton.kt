package com.idealcn.event.study

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import java.util.jar.Attributes


/**
 * author: guoning
 * date: 2018/7/10 2:41 PM
 * description:
 */
class MyButton : Button {


    constructor( context: Context) : super(context)

    constructor( context: Context,attributes: AttributeSet) : super(context,attributes)

    constructor(context: Context,attributes: AttributeSet,defStyleAttr : Int) : super(context,attributes,defStyleAttr)


}