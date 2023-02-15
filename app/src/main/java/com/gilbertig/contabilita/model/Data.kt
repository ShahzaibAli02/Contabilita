package com.gilbertig.contabilita.model

data class Data(
    var id:String="",
    var Art:String="",
    var Descrizione:String="",
    var Descrizione2:String="",
    var UM1:String="",
    var UM2:String="",
    var MLunghezza: Double = 0.0,
    var MLunghezza2: Double = 0.0,
    var MAltezza: Double = 0.0,
    var mTotal: Double = 0.0,
    var MADLunghezza: Double = 0.0,
    var MADLunghezza2: Double = 0.0,
    var MADAltezza: Double = 0.0,
    var Qta: Double = 0.0,
    var Prezzo: Double = 0.0,
    var Importo: Double = 0.0,
)
{
    constructor() : this("","","","","","",0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
}
