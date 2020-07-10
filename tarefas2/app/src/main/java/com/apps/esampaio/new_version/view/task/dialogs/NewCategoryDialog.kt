package com.apps.esampaio.new_version.view.task.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.esampaio.R
import com.apps.esampaio.new_version.view.utils.DisableViewTextWatcher
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.adapter.MaterialColorPickerAdapter
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.github.dhaval2404.colorpicker.util.ColorUtil
interface NewCategoryDialogListener {
    fun onSave(categoryName:String, selectedColor: String);

    fun onCancel();
}
class NewCategoryDialog(private val ctx:Context) : Dialog(ctx) {

    private var builder: AlertDialog.Builder = AlertDialog.Builder(context);
    private var colorShape = ColorShape.CIRCLE

    private var colorListView : RecyclerView;
    private var colorListAdapter: MaterialColorPickerAdapter;
    private var categoryName : TextView;
    private var saveButton : Button;
    private var dialog: Dialog? = null;

    var listener: NewCategoryDialogListener? = null;

    init{
        val view = LayoutInflater.from(ctx).inflate(R.layout.new_category_dialog, null, false);
        colorListView = view.findViewById(R.id.colors_list);
        categoryName = view.findViewById(R.id.new_category_name);
        saveButton = view.findViewById(R.id.save_category_button)
        saveButton.setOnClickListener {
            saveCategory();
        }

        val colorList = ctx.resources.getStringArray(R.array.colors);
        colorListAdapter = MaterialColorPickerAdapter(colorList.toList())
        colorListAdapter.setColorShape(colorShape)

        colorListAdapter.setDefaultColor(colorList[0]);

        colorListView.adapter = colorListAdapter;
        colorListView.layoutManager = GridLayoutManager(ctx,5);
        colorListView.setHasFixedSize(true)

        saveButton.isEnabled = false;

        categoryName.addTextChangedListener( DisableViewTextWatcher(saveButton));


        builder.setView(view)
        builder.setOnCancelListener {
            listener?.onCancel()
        }

    }
    private fun saveCategory() {
        if(dialog != null){
            val categoryName = categoryName.text.toString();
            val selectedColor = colorListAdapter.getSelectedColor();
            listener?.onSave(categoryName,selectedColor);
            dialog?.dismiss()
        }


    }
    override fun show() {
        dialog = builder.show()
    }
}