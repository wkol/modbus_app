package com.example.modbus

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ValueElement(val label: String, val unit: String, val value: String)

class Reading(
    var date: String,
    var total_power: Double = 0.0,
    var total_reactive_power: Double = 0.0,
    var total_apparent_power: Double = 0.0,
    var power_l1: Double = 0.0,
    var power_l2: Double = 0.0,
    var power_l3: Double = 0.0,
    var reactive_power_l1: Double = 0.0,
    var reactive_power_l2: Double = 0.0,
    var reactive_power_l3: Double = 0.0,
    var voltage_13: Double = 0.0,
    var voltage_12: Double = 0.0,
    var voltage_23: Double = 0.0,
    var voltage_l1: Double = 0.0,
    var voltage_l2: Double = 0.0,
    var voltage_l3: Double = 0.0,
    var current_l1: Double = 0.0,
    var current_l2: Double = 0.0,
    var current_l3: Double = 0.0,
    var current_n: Double = 0.0,
    var frequency: Double = 0.0,
    var total_cos: Double = 0.0,
    var cos_l1: Double = 0.0,
    var cos_l2: Double = 0.0,
    var cos_l3: Double = 0.0,
    var input_EA: Double = 0.0,
    var return_EA: Double = 0.0,
    var ind_EQ: Double = 0.0,
    var cap_EQ: Double = 0.0,
    var power_DC: Double = 0.0,
    var voltage_DC: Double = 0.0,
    var current_DC: Double = 0.0,
    var power_inv: Double = 0.0,
    var reactive_power_inv: Double = 0.0,
    var apparent_power_inv: Double = 0.0,
    var voltage_UAB_inv: Double = 0.0,
    var voltage_UBC_inv: Double = 0.0,
    var voltage_UCA_inv: Double = 0.0,
    var voltage_UA_inv: Double = 0.0,
    var voltage_UB_inv: Double = 0.0,
    var voltage_UC_inv: Double = 0.0,
    var current_A_inv: Double = 0.0,
    var current_B_inv: Double = 0.0,
    var current_C_inv: Double = 0.0,
    var current_avg_inv: Double = 0.0,
    var frequency_inv: Double = 0.0,
    var cos_inv: Double = 0.0,
    var heat_sink_temp_inv: Double = 0.0,
    var energy: Double = 0.0,
    var state_1_inv: Double = 0.0,
    var state_2_inv: Double = 0.0
) {
    fun getCategoriesList(): List<Any> {
        return listOf(
            Category(
                "Moce",
                listOf(
                    ValueElement("Moc całkowita:", "kW", total_power.toString()),
                    ValueElement("Moc całkowita bierna:", "kvar", total_reactive_power.toString()),
                    ValueElement("Moc całkowita pozorna:", "kVA", total_apparent_power.toString()),
                    ValueElement("Moc l1:", "kW", power_l1.toString()),
                    ValueElement("Moc l2:", "kW", power_l2.toString()),
                    ValueElement("Moc l3:", "kW", power_l3.toString()),
                    ValueElement("Moc bierna l1:", "kvar", reactive_power_l1.toString()),
                    ValueElement("Moc bierna l2:", "kvar", reactive_power_l2.toString()),
                    ValueElement("Moc bierna l3", "kvar", reactive_power_l3.toString()),
                    ValueElement("Moc DC:", "W", power_DC.toString())
                )
            ),
            Category(
                "Energie",
                listOf(
                    ValueElement("Energia pozorna pobrana:", "kWh", input_EA.toString()),
                    ValueElement("Energia pozorna oddana:", "kWh", return_EA.toString()),
                    ValueElement("Energia bierna indukcyjna:", "kvarh", ind_EQ.toString()),
                    ValueElement("EQ pojemnościowa:", "kvarh", cap_EQ.toString()),
                    ValueElement("Energia:", "Wh", energy.toString())
                )
            ),
            Category(
                "Napięcia",
                listOf(
                    ValueElement("Napięcie 13:", "V", voltage_13.toString()),
                    ValueElement("Napięcie 12:", "V", voltage_12.toString()),
                    ValueElement("Napięcie 23:", "V", voltage_23.toString()),
                    ValueElement("Napięcie l1:", "V", voltage_l1.toString()),
                    ValueElement("Napięcie l2:", "V", voltage_l2.toString()),
                    ValueElement("Napięcie l3:", "V", voltage_l3.toString()),
                    ValueElement("Napięcie DC:", "V", voltage_DC.toString()),
                )
            ),
            Category(
                "Natężenia",
                listOf(
                    ValueElement("Natężenie l1:", "A", current_l1.toString()),
                    ValueElement("Natężenie l2:", "A", current_l2.toString()),
                    ValueElement("Natężenie l3:", "A", current_l3.toString()),
                    ValueElement("Natężenie n:", "A", current_n.toString()),
                    ValueElement("Natężenie DC:", "A", current_DC.toString()),
                )
            ),
            Category(
                "Inne",
                listOf(
                    ValueElement("Cos l1:", "", cos_l1.toString()),
                    ValueElement("Cos l2:", "", cos_l2.toString()),
                    ValueElement("Cos l3:", "", cos_l3.toString()),
                    ValueElement("Częstotliwość:", "Hz", frequency.toString()),
                    ValueElement("Cos całkowity:", "", total_cos.toString())
                )
            ),
            Category(
                "Inwerter",
                listOf(
                    ValueElement("Moc:", "W", power_inv.toString()),
                    ValueElement("Moc bierna:", "var", reactive_power_inv.toString()),
                    ValueElement("Moc pozorna:", "VA", apparent_power_inv.toString()),
                    ValueElement("Napięcie AB:", "V", voltage_UAB_inv.toString()),
                    ValueElement("Napięcie BC:", "V", voltage_UBC_inv.toString()),
                    ValueElement("Napięcie CA:", "V", voltage_UCA_inv.toString()),
                    ValueElement("Napięcie A:", "V", voltage_UA_inv.toString()),
                    ValueElement("Napięcie B:", "V", voltage_UB_inv.toString()),
                    ValueElement("Napięcie C:", "V", voltage_UC_inv.toString()),
                    ValueElement("Natężenie A:", "A", current_A_inv.toString()),
                    ValueElement("Natężenie B:", "A", current_B_inv.toString()),
                    ValueElement("Natężenie C:", "A", current_C_inv.toString()),
                    ValueElement("Prąd średni:", "A", current_avg_inv.toString()),
                    ValueElement("Czeęstotliwość:", "HZ", frequency_inv.toString()),
                    ValueElement("Cos:", "", cos_inv.toString()),
                    ValueElement("Temperatura radiatora:", "°C", heat_sink_temp_inv.toString()),
                    ValueElement("Status inwertera:", "", state_1_inv.toString()),
                    ValueElement("Status inwertera 2:", "", state_2_inv.toString()),

                )
            )
        )
    }
    // fun getFlattenCategories(): List<Any> {
    //     val x = mutableListOf<Any>()
    //     x.addAll(
    //         Category(
    //             "Moce", listOf(
    //                 ValueElement("Moc całkowita:", "kW", total_power.toString()),
    //                 ValueElement("Moc całkowita bierna:", "kvar", total_reactive_power.toString()),
    //                 ValueElement("Moc całkowita pozorna:", "kVA", total_apparent_power.toString()),
    //                 ValueElement("Moc l1:", "kW", power_l1.toString()),
    //                 ValueElement("Moc l2:", "kW", power_l2.toString()),
    //                 ValueElement("Moc l3:", "kW", power_l3.toString()),
    //                 ValueElement("Moc bierna l1:", "kvar", reactive_power_l1.toString()),
    //                 ValueElement("Moc bierna l2:", "kvar", reactive_power_l2.toString()),
    //                 ValueElement("Moc bierna l3", "kvar", reactive_power_l3.toString())
    //             )
    //         ).flattenCategory()
    //     )
    //     x.addAll(
    //         Category(
    //             "Energie", listOf(
    //                 ValueElement("Energia pozorna pobrana:", "kWh", input_EA.toString()),
    //                 ValueElement("EA pobrana MSB:", "kWh", input_EA_MSB.toString()),
    //                 ValueElement("Energia pozorna oddana:", "kWh", return_EA.toString()),
    //                 ValueElement("EA oddana MSB:", "kWh", return_EA_MSB.toString()),
    //                 ValueElement("Energia bierna indukcyjna:", "kvarh", ind_EQ.toString()),
    //                 ValueElement("EQ indukcyjna MSB:", "kvarh", ind_EQ_MSB.toString()),
    //                 ValueElement("EQ pojemnościowa:", "kvarh", cap_EQ.toString()),
    //                 ValueElement("EQ pojemnościowa MSB:", "kvarh", cap_EQ_MSB.toString())
    //             )
    //         ).flattenCategory()
    //     )
    //     x.addAll(
    //         Category(
    //             "Napięcia", listOf(
    //                 ValueElement("Napięcie 13:", "V", voltage_13.toString()),
    //                 ValueElement("Napięcie 12:", "V", voltage_12.toString()),
    //                 ValueElement("Napięcie 23:", "V", voltage_23.toString()),
    //                 ValueElement("Napięcie l1:", "V", voltage_l1.toString()),
    //                 ValueElement("Napięcie l2:", "V", voltage_l2.toString()),
    //                 ValueElement("Napięcie l3:", "V", voltage_l3.toString())
    //             )
    //         ).flattenCategory()
    //     )
    //     x.addAll(
    //         Category(
    //             "Natężenia", listOf(
    //                 ValueElement("Natężenie l1:", "A", current_l1.toString()),
    //                 ValueElement("Natężenie l2:", "A", current_l2.toString()),
    //                 ValueElement("Natężenie l3:", "A", current_l3.toString()),
    //                 ValueElement("Natężenie n:", "A", current_n.toString())
    //             )
    //         ).flattenCategory()
    //     )
    //     x.addAll(
    //         Category(
    //             "Inne", listOf(
    //                 ValueElement("Cos l1:", "", cos_l1.toString()),
    //                 ValueElement("Cos l2:", "", cos_l2.toString()),
    //                 ValueElement("Cos l3:", "", cos_l3.toString()),
    //                 ValueElement("Częstotliwość:", "Hz", frequency.toString()),
    //                 ValueElement("Cos całkowity:", "", total_cos.toString())
    //             )
    //         ).flattenCategory()
    //     )
    //     return x
    // }
    fun getDateString(): String {
        return date.replace("T", " ")
    }

    fun getFieldsList(): List<Double> {
        return listOf(
            total_power,
            total_reactive_power,
            total_apparent_power,
            power_l1,
            power_l2,
            power_l3,
            reactive_power_l1,
            reactive_power_l2,
            reactive_power_l3,
            voltage_13,
            voltage_12,
            voltage_23,
            voltage_l1,
            voltage_l2,
            voltage_l3,
            current_l1,
            current_l2,
            current_l3,
            current_n,
            frequency,
            total_cos,
            cos_l1,
            cos_l2,
            cos_l3,
            input_EA,
            return_EA,
            ind_EQ,
            cap_EQ,
            power_DC,
            voltage_DC,
            current_DC,
            power_inv,
            reactive_power_inv,
            apparent_power_inv,
            voltage_UAB_inv,
            voltage_UBC_inv,
            voltage_UCA_inv,
            voltage_UA_inv,
            voltage_UB_inv,
            voltage_UC_inv,
            current_A_inv,
            current_B_inv,
            current_C_inv,
            current_avg_inv,
            frequency_inv,
            cos_inv,
            heat_sink_temp_inv,
            energy,
            state_1_inv,
            state_2_inv
        )
    }
}

data class Category(
    val name: String,
    var elements: List<ValueElement>,
    var isExpanded: Boolean = false
) {
    fun flattenCategory(): MutableList<Any> {
        val x = mutableListOf<Any>(this)
        x.addAll(elements)
        return x
    }
}

class AxisDateFormatter(val startDate: String) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        val newFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val originalTimestamp: Long = (format.parse(startDate) as Date).time + value.toLong()
        val dateNew = Date()
        return try {
            dateNew.time = originalTimestamp
            newFormat.format(dateNew).toString()
        } catch (ex: Exception) {
            "xx"
        }
    }
}
