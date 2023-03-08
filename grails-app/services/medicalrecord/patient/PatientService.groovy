package medicalrecord.patient

import grails.gorm.transactions.Transactional
import medicalrecord.user.Doctor

@Transactional
class PatientService {

    Patient addPatientToDoctor(Patient patient, Doctor doctor) {
        patient.doctor = doctor
        patient.save(flush: true)
    }

}
