package medicalrecord.patient

import medicalrecord.user.Doctor

class Patient {
    String name
    Date birthDate
    String address
    String phone
    Doctor doctor
    static mapWith = "mongo"

    static constraints = {
        name blank: false
        birthDate nullable: false
        address nullable: true, maxSize: 255
        phone nullable: true, maxSize: 255
        doctor nullable: false
    }
}
