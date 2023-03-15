package medicalrecord.appoitment

import medicalrecord.patient.Patient
import medicalrecord.user.Doctor

class MedicalRecord {
    Patient patient
    Doctor doctor
    String complaint
    String diagnosis
    String treatment
    String observation
    List<String> medicines
    String createdAt
    static mapWith = "mongo"
    static constraints = {
        doctor nullable: false
        patient nullable: false
        complaint nullable: true, maxSize: 255
        diagnosis nullable: true, maxSize: 255
        treatment nullable: true, maxSize: 255
        observation nullable: true, maxSize: 255
        createdAt nullable: false
        medicines nullable: true, maxSize: 255
    }
}
