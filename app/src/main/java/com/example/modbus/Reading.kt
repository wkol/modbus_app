package com.example.modbus

data class ValueElement(val label: String, val unit: String, val value: String)

class Reading(var date: String,
              var voltage_13: Double = 0.0,
              var voltage_12: Double = 0.0,
              var voltage_23: Double = 0.0,
              var current_l1: Double = 0.0,
              var current_l2: Double = 0.0,
              var current_l3: Double = 0.0,
              var total_power: Double = 0.0,
              var total_reactive_power: Double = 0.0,
              var total_apparent_power: Double = 0.0,
              var frequency: Double = 0.0,
              var total_cos: Double = 0.0,
              var current_n: Double = 0.0,
              var input_EA: Double = 0.0,
              var input_EA_MSB: Double = 0.0,
              var return_EA: Double = 0.0,
              var return_EA_MSB: Double = 0.0,
              var ind_EQ: Double = 0.0,
              var ind_EQ_MSB: Double = 0.0,
              var cap_EQ: Double = 0.0,
              var cap_EQ_MSB: Double = 0.0,
              var voltage_l1: Double = 0.0,
              var voltage_l2: Double = 0.0,
              var voltage_l3: Double = 0.0,
              var power_l1: Double = 0.0,
              var power_l2: Double = 0.0,
              var power_l3: Double = 0.0,
              var reactive_power_l1: Double = 0.0,
              var reactive_power_l2: Double = 0.0,
              var reactive_power_l3: Double = 0.0,
              var cos_l1: Double = 0.0,
              var cos_l2: Double = 0.0,
              var cos_l3: Double = 0.0
) {
    fun getValueElements(): List<ValueElement> {
        return listOf(//ValueElement("Czas odczytu:", "", date.replace("T", " ")),
                ValueElement("Napięcie 13:", "V", voltage_13.toString()),
                ValueElement("Napięcie 12:", "V", voltage_12.toString()),
                ValueElement("Napięcie 23:", "V", voltage_23.toString()),
                ValueElement("Natężenie l1:", "A", current_l1.toString()),
                ValueElement("Natężenie l2:", "A", current_l2.toString()),
                ValueElement("Natężenie l3:", "A", current_l3.toString()),
                ValueElement("Moc całkowita:", "kW", total_power.toString()),
                ValueElement("Moc całkowita bierna:", "kvar", total_reactive_power.toString()),
                ValueElement("Moc całkowita pozorna:", "kVA", total_apparent_power.toString()),
                ValueElement("Częstotliwość:", "Hz", frequency.toString()),
                ValueElement("Cos całkowity:", "", total_cos.toString()),
                ValueElement("Natężenie n:", "A", current_n.toString()),
                ValueElement("Energia pozorna pobrana:", "kWh", input_EA.toString()),
                ValueElement("EA pobrana MSB:", "kWh", input_EA_MSB.toString()),
                ValueElement("Energia pozorna oddana:", "kWh", return_EA.toString()),
                ValueElement("EA oddana MSB:", "kWh", return_EA_MSB.toString()),
                ValueElement("Energia bierna indukcyjna:", "kvarh", ind_EQ.toString()),
                ValueElement("EQ indukcyjna MSB:", "kvarh", ind_EQ_MSB.toString()),
                ValueElement("EQ pojemnościowa:", "kvarh", cap_EQ.toString()),
                ValueElement("EQ pojemnościowa MSB:", "kvarh", cap_EQ_MSB.toString()),
                ValueElement("Napięcie l1:", "V", voltage_l1.toString()),
                ValueElement("Napięcie l2:", "V", voltage_l2.toString()),
                ValueElement("Napięcie l3:", "V", voltage_l3.toString()),
                ValueElement("Moc l1:", "kW", power_l1.toString()),
                ValueElement("Moc l2:", "kW", power_l2.toString()),
                ValueElement("Moc l3:", "kW", power_l3.toString()),
                ValueElement("Moc bierna l1:", "kvar", reactive_power_l1.toString()),
                ValueElement("Moc bierna l2:", "kvar", reactive_power_l2.toString()),
                ValueElement("Moc bierna l3", "kvar", reactive_power_l3.toString()),
                ValueElement("Cos l1:", "", cos_l1.toString()),
                ValueElement("Cos l2:", "", cos_l2.toString()),
                ValueElement("Cos l3:", "", cos_l3.toString())
        )
    }
    fun createCategories(): List<Category> {
        return  listOf(
            Category("Napięcia", listOf(
                ValueElement("Napięcie 13:", "V", voltage_13.toString()),
                ValueElement("Napięcie 12:", "V", voltage_12.toString()),
                ValueElement("Napięcie 23:", "V", voltage_23.toString()),
                ValueElement("Napięcie l1:", "V", voltage_l1.toString()),
                ValueElement("Napięcie l2:", "V", voltage_l2.toString()),
                ValueElement("Napięcie l3:", "V", voltage_l3.toString()))),
            Category("Natężenia", listOf(
                ValueElement("Natężenie l1:", "A", current_l1.toString()),
                ValueElement("Natężenie l2:", "A", current_l2.toString()),
                ValueElement("Natężenie l3:", "A", current_l3.toString()),
                ValueElement("Natężenie n:", "A", current_n.toString()))),
            Category("Moce", listOf(
                ValueElement("Moc całkowita:", "kW", total_power.toString()),
                ValueElement("Moc całkowita bierna:", "kvar", total_reactive_power.toString()),
                ValueElement("Moc całkowita pozorna:", "kVA", total_apparent_power.toString()),
                ValueElement("Moc l1:", "kW", power_l1.toString()),
                ValueElement("Moc l2:", "kW", power_l2.toString()),
                ValueElement("Moc l3:", "kW", power_l3.toString()),
                ValueElement("Moc bierna l1:", "kvar", reactive_power_l1.toString()),
                ValueElement("Moc bierna l2:", "kvar", reactive_power_l2.toString()),
                ValueElement("Moc bierna l3", "kvar", reactive_power_l3.toString()))),
            Category("Energie", listOf(
                ValueElement("Energia pozorna pobrana:", "kWh", input_EA.toString()),
                ValueElement("EA pobrana MSB:", "kWh", input_EA_MSB.toString()),
                ValueElement("Energia pozorna oddana:", "kWh", return_EA.toString()),
                ValueElement("EA oddana MSB:", "kWh", return_EA_MSB.toString()),
                ValueElement("Energia bierna indukcyjna:", "kvarh", ind_EQ.toString()),
                ValueElement("EQ indukcyjna MSB:", "kvarh", ind_EQ_MSB.toString()),
                ValueElement("EQ pojemnościowa:", "kvarh", cap_EQ.toString()),
                ValueElement("EQ pojemnościowa MSB:", "kvarh", cap_EQ_MSB.toString()))),
            Category("Inne", listOf(
                ValueElement("Cos l1:", "", cos_l1.toString()),
                ValueElement("Cos l2:", "", cos_l2.toString()),
                ValueElement("Cos l3:", "", cos_l3.toString()),
                ValueElement("Częstotliwość:", "Hz", frequency.toString()),
                ValueElement("Cos całkowity:", "", total_cos.toString()))))
    }
    fun getDateString(): String {
        return date.replace("T", " ")
    }
}

data class Category(val name: String, val elements: List<ValueElement>, var isExpanded: Boolean = false)


