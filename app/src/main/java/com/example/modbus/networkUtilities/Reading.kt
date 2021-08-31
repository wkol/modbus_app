package com.example.modbus.networkUtilities

data class ValueElement(val label: String, val unit: String, val value: String)
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
                    ValueElement("Moc DC:", "W", power_dc.toString())
                )
            ),
            Category(
                "Energie",
                listOf(
                    ValueElement("Energia pozorna pobrana:", "kWh", input_ea.toString()),
                    ValueElement("Energia pozorna oddana:", "kWh", return_ea.toString()),
                    ValueElement("Energia bierna indukcyjna:", "kvarh", ind_eq.toString()),
                    ValueElement("EQ pojemnościowa:", "kvarh", cap_eq.toString()),
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
                    ValueElement("Napięcie DC:", "V", voltage_dc.toString())
                )
            ),
            Category(
                "Natężenia",
                listOf(
                    ValueElement("Natężenie l1:", "A", current_l1.toString()),
                    ValueElement("Natężenie l2:", "A", current_l2.toString()),
                    ValueElement("Natężenie l3:", "A", current_l3.toString()),
                    ValueElement("Natężenie n:", "A", current_n.toString()),
                    ValueElement("Natężenie DC:", "A", current_dc.toString())
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
                    ValueElement("Napięcie AB:", "V", voltage_uab_inv.toString()),
                    ValueElement("Napięcie BC:", "V", voltage_ubc_inv.toString()),
                    ValueElement("Napięcie CA:", "V", voltage_uca_inv.toString()),
                    ValueElement("Napięcie A:", "V", voltage_ua_inv.toString()),
                    ValueElement("Napięcie B:", "V", voltage_ub_inv.toString()),
                    ValueElement("Napięcie C:", "V", voltage_uc_inv.toString()),
                    ValueElement("Natężenie A:", "A", current_a_inv.toString()),
                    ValueElement("Natężenie B:", "A", current_b_inv.toString()),
                    ValueElement("Natężenie C:", "A", current_c_inv.toString()),
                    ValueElement("Prąd średni:", "A", current_avg_inv.toString()),
                    ValueElement("Czeęstotliwość:", "HZ", frequency_inv.toString()),
                    ValueElement("Cos:", "", cos_inv.toString()),
                    ValueElement("Temperatura radiatora:", "°C", heat_sink_temp_inv.toString()),
                    ValueElement("Status inwertera:", "", state_1_inv.toString()),
                    ValueElement("Status inwertera 2:", "", state_2_inv.toString())

                )
            )
        )
    }

    fun getDateString(): String {
        return date.replace("T", " ")
    }


}

data class Category(
    val name: String,
    var elements: List<ValueElement>,
    var isExpanded: Boolean = false
)

