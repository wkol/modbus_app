package com.example.modbus.networkUtilities

import com.example.modbus.readingsModbus.ReadingItem
import com.example.modbus.readingsModbus.ReadingItem.Category
import com.example.modbus.readingsModbus.ReadingItem.ValueElement

data class Reading(
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
    var input_ea: Double = 0.0,
    var return_ea: Double = 0.0,
    var ind_eq: Double = 0.0,
    var cap_eq: Double = 0.0,
    var power_dc: Double = 0.0,
    var voltage_dc: Double = 0.0,
    var current_dc: Double = 0.0,
    var power_inv: Double = 0.0,
    var reactive_power_inv: Double = 0.0,
    var apparent_power_inv: Double = 0.0,
    var voltage_uab_inv: Double = 0.0,
    var voltage_ubc_inv: Double = 0.0,
    var voltage_uca_inv: Double = 0.0,
    var voltage_ua_inv: Double = 0.0,
    var voltage_ub_inv: Double = 0.0,
    var voltage_uc_inv: Double = 0.0,
    var current_a_inv: Double = 0.0,
    var current_b_inv: Double = 0.0,
    var current_c_inv: Double = 0.0,
    var current_avg_inv: Double = 0.0,
    var frequency_inv: Double = 0.0,
    var cos_inv: Double = 0.0,
    var heat_sink_temp_inv: Double = 0.0,
    var energy: Double = 0.0,
    var state_1_inv: Double = 0.0,
    var state_2_inv: Double = 0.0
) {
    fun getCategoriesList(): List<ReadingItem> {
        return listOf(
            Category(
                "Moce",
                listOf(
                    ValueElement("Moc całkowita:", "kW", total_power),
                    ValueElement("Moc całkowita bierna:", "kvar", total_reactive_power),
                    ValueElement("Moc całkowita pozorna:", "kVA", total_apparent_power),
                    ValueElement("Moc l1:", "kW", power_l1),
                    ValueElement("Moc l2:", "kW", power_l2),
                    ValueElement("Moc l3:", "kW", power_l3),
                    ValueElement("Moc bierna l1:", "kvar", reactive_power_l1),
                    ValueElement("Moc bierna l2:", "kvar", reactive_power_l2),
                    ValueElement("Moc bierna l3", "kvar", reactive_power_l3),
                    ValueElement("Moc DC:", "W", power_dc)
                )
            ),
            Category(
                "Energie",
                listOf(
                    ValueElement("Energia pozorna pobrana:", "kWh", input_ea),
                    ValueElement("Energia pozorna oddana:", "kWh", return_ea),
                    ValueElement("Energia bierna indukcyjna:", "kvarh", ind_eq),
                    ValueElement("EQ pojemnościowa:", "kvarh", cap_eq),
                    ValueElement("Energia:", "Wh", energy)
                )
            ),
            Category(
                "Napięcia",
                listOf(
                    ValueElement("Napięcie 13:", "V", voltage_13),
                    ValueElement("Napięcie 12:", "V", voltage_12),
                    ValueElement("Napięcie 23:", "V", voltage_23),
                    ValueElement("Napięcie l1:", "V", voltage_l1),
                    ValueElement("Napięcie l2:", "V", voltage_l2),
                    ValueElement("Napięcie l3:", "V", voltage_l3),
                    ValueElement("Napięcie DC:", "V", voltage_dc)
                )
            ),
            Category(
                "Natężenia",
                listOf(
                    ValueElement("Natężenie l1:", "A", current_l1),
                    ValueElement("Natężenie l2:", "A", current_l2),
                    ValueElement("Natężenie l3:", "A", current_l3),
                    ValueElement("Natężenie n:", "A", current_n),
                    ValueElement("Natężenie DC:", "A", current_dc)
                )
            ),
            Category(
                "Inne",
                listOf(
                    ValueElement("Cos l1:", "", cos_l1),
                    ValueElement("Cos l2:", "", cos_l2),
                    ValueElement("Cos l3:", "", cos_l3),
                    ValueElement("Częstotliwość:", "Hz", frequency),
                    ValueElement("Cos całkowity:", "", total_cos)
                )
            ),
            Category(
                "Inwerter",
                listOf(
                    ValueElement("Moc:", "W", power_inv),
                    ValueElement("Moc bierna:", "var", reactive_power_inv),
                    ValueElement("Moc pozorna:", "VA", apparent_power_inv),
                    ValueElement("Napięcie AB:", "V", voltage_uab_inv),
                    ValueElement("Napięcie BC:", "V", voltage_ubc_inv),
                    ValueElement("Napięcie CA:", "V", voltage_uca_inv),
                    ValueElement("Napięcie A:", "V", voltage_ua_inv),
                    ValueElement("Napięcie B:", "V", voltage_ub_inv),
                    ValueElement("Napięcie C:", "V", voltage_uc_inv),
                    ValueElement("Natężenie A:", "A", current_a_inv),
                    ValueElement("Natężenie B:", "A", current_b_inv),
                    ValueElement("Natężenie C:", "A", current_c_inv),
                    ValueElement("Prąd średni:", "A", current_avg_inv),
                    ValueElement("Czeęstotliwość:", "HZ", frequency_inv),
                    ValueElement("Cos:", "", cos_inv),
                    ValueElement("Temperatura radiatora:", "°C", heat_sink_temp_inv),
                    ValueElement("Status inwertera:", "", state_1_inv),
                    ValueElement("Status inwertera 2:", "", state_2_inv)
                )
            )
        )
    }

    fun getDateString(): String {
        return date.replace("T", " ")
    }

}





