package medicalrecord.patient

import grails.gorm.transactions.Transactional
import medicalrecord.user.Doctor

@Transactional
class PatientService {

    Patient addPatientToDoctor(Patient patient, Doctor doctor) {
        patient.doctor = doctor
        patient.save(flush: true)
    }

    List<Patient> getPatientsByDoctor(Doctor doctor) {
        List<Patient> listOfPatients = Patient.findAllByDoctor(doctor)
        return listOfPatients
    }

}
