package medicalrecord.appoitment

import medicalrecord.patient.Patient

class MedicalRecord {
    Patient patient
    String complaint
    String diagnosis
    String treatment
    String observations
    List<String> medicines
    static mapWith = "mongo"
    static constraints = {
        patient nullable: false
        complaint nullable: true, maxSize: 255
        diagnosis nullable: true, maxSize: 255
        treatment nullable: true, maxSize: 255
        observations nullable: true, maxSize: 255
        medicines nullable: true, maxSize: 255
    }
}
