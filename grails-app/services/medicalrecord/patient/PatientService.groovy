package medicalrecord.patient

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
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

    Patient getPatientById(String id) {
        Patient patient = Patient.get(id)
        return patient
    }

    List<String> getAllPatientsMedicalNumbers() {
        List<String> listOfMedicalNumbers = Patient.findAll().collect { it.medicalNumber }
        return listOfMedicalNumbers
    }

    void deletePatient(GrailsParameterMap params) {
        Patient patient = Patient.get(params.id)
        patient.delete()
    }

    def getPatient(Long id) {
        Patient.get(id)
    }

    def updatePatient(Patient patient) {
        patient.save(flush: true)
    }

}
