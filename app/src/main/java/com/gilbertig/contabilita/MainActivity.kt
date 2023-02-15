package com.gilbertig.contabilita

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.gilbertig.contabilita.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputLayout

private var TextInputLayout.text: String
    get()
    {
        return this.editText!!.text.toString()
    }
    set(value:String)
    {
        this.editText?.setText(value)
    }

class MainActivity : AppCompatActivity()
{

    private val xlsSheetManager:XlsSheetManager by lazy {
        XlsSheetManager(this@MainActivity)
    }
     private val viewModel: MainViewModel by lazy {
         ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
    }
     private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        viewModel.getData().let {

            binding.txtInputMLunghezza.text=it.MLunghezza.toString()
            binding.txtInputMLunghezza2.text=it.MLunghezza2.toString()
            binding.txtInputMAltezza.text=it.MAltezza.toString()


            binding.txtInputMDLunghezza.text=it.MADLunghezza.toString()
            binding.txtInputMDLunghezza2.text=it.MADLunghezza2.toString()
            binding.txtInputMDAltezza.text=it.MADAltezza.toString()


            binding.txtInputPrezzo.text=it.Prezzo.toString()





        }

        addTextWatchers()
        viewModel.listenForUpdates().observe(this) {

            val MTotal=it.MLunghezza*it.MLunghezza2*it.MAltezza

            val QTA=MTotal-(it.MADLunghezza*it.MADLunghezza2*it.MADAltezza)

            val Importo=QTA*it.Prezzo


            binding.txtInputImporto.text=Importo.toString()

            binding.txtMTotal.text= "Tot. = $MTotal"

            binding.txtInputQTA.text=QTA.toString()


            viewModel.getData().mTotal=MTotal
            viewModel.getData().Qta=QTA
            viewModel.getData().Importo=Importo


        }

        binding.btnReset.setOnClickListener {
            binding.txtInputMAltezza.text=""
            binding.txtInputMLunghezza.text=""
            binding.txtInputMLunghezza2.text=""
        }
        binding.btnCancel.setOnClickListener {

            binding.apply {
                val editTexts= listOf(txtinputId,txtinputArticolo,txtinputDescrizione, txtInputMLunghezza,txtInputMLunghezza2,txtInputMAltezza,txtInputMDLunghezza,txtInputMDLunghezza2,txtInputMDAltezza,txtInputPrezzo,txtInputQTA,txtinputUM1,txtinputUM2)
                editTexts.forEach {
                    it.text=""
                }
            }

        }
        binding.btnSave.setOnClickListener {


            xlsSheetManager.addRow(viewModel.getData())
            xlsSheetManager.save()


        }


        checkWritePermission()
    }

    private fun checkWritePermission()
    {
        if (ContextCompat.checkSelfPermission(this,"android.permission.WRITE_EXTERNAL_STORAGE")
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf("android.permission.WRITE_EXTERNAL_STORAGE"), 0)
        }


    }

    private fun addTextWatchers()
    {
        setTextWatcher(binding.txtInputMAltezza)
        setTextWatcher(binding.txtInputMLunghezza)
        setTextWatcher(binding.txtInputMLunghezza2)
        setTextWatcher(binding.txtInputMDAltezza)
        setTextWatcher(binding.txtInputMDLunghezza)
        setTextWatcher(binding.txtInputMDLunghezza2)
        setTextWatcher(binding.txtInputPrezzo)

        setTextWatcher(binding.txtinputId)
        setTextWatcher(binding.txtinputArticolo)
        setTextWatcher(binding.txtinputDescrizione)
        setTextWatcher(binding.txtinputUM1)
        setTextWatcher(binding.txtinputDescrizione2)
        setTextWatcher(binding.txtinputUM2)



    }

    private fun setTextWatcher(txtLayout: TextInputLayout)
    {

        txtLayout.editText!!.addTextChangedListener {

            val text = it.toString()
            val data = viewModel.getData()
            when (txtLayout)
            {
                binding.txtinputId -> data.id=text
                binding.txtinputArticolo -> data.Art=text
                binding.txtinputDescrizione -> data.Descrizione=text
                binding.txtinputUM1 -> data.UM1=text
                binding.txtinputDescrizione2 -> data.Descrizione2=text
                binding.txtinputUM2 -> data.UM2=text

                binding.txtInputMAltezza -> data.MAltezza = if(text.isEmpty()) 0.0 else  text.toDouble()
                binding.txtInputMLunghezza -> data.MLunghezza = if(text.isEmpty()) 0.0 else  text.toDouble()
                binding.txtInputMLunghezza2 -> data.MLunghezza2 = if(text.isEmpty()) 0.0 else  text.toDouble()
                binding.txtInputMDAltezza -> data.MADAltezza = if(text.isEmpty()) 0.0 else  text.toDouble()
                binding.txtInputMDLunghezza -> data.MADLunghezza = if(text.isEmpty()) 0.0 else  text.toDouble()
                binding.txtInputMDLunghezza2 -> data.MADLunghezza2 =if(text.isEmpty()) 0.0 else  text.toDouble()
                binding.txtInputPrezzo -> data.Prezzo =if(text.isEmpty()) 0.0 else  text.toDouble()
            }
            viewModel.changeVal(data)
        }

    }


}