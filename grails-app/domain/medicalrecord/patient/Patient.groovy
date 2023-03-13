package medicalrecord.patient

import medicalrecord.user.Doctor

class Patient {
    String medicalNumber
    String name
    Date birthDate
    String address
    String phone
    Doctor doctor
    String gender
    static mapWith = "mongo"

    static constraints = {
        medicalNumber blank: false, unique: true
        name blank: false
        birthDate nullable: false
        address nullable: true, maxSize: 255
        phone nullable: true, maxSize: 255
        gender nullable: false, maxSize: 255
        doctor nullable: false
    }
}
