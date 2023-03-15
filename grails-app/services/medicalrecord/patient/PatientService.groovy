package medicalrecord.patient

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import medicalrecord.appoitment.MedicalRecord
import medicalrecord.user.Doctor

@Transactional
class PatientService {

    Patient addPatientToDoctor(Patient patient, Doctor doctor) {
        patient.doctor = doctor
        patient.save(flush: true)
    }

    List<Patient> getPatientsByDoctor(Doctor doctor) {
        List<Patient> listOfPatients = Patient.findAllByDoctor(doctor)
        for (Patient patient : listOfPatients) {
            patient.doctor = null
            // Get the last medical record of the patient
            List<MedicalRecord> listOfMedicalRecords = MedicalRecord.findAllByPatient(patient)
            if (listOfMedicalRecords.size() > 0) {
                patient.lastVisited = listOfMedicalRecords.get(listOfMedicalRecords.size() - 1).createdAt
            }else{
                patient.lastVisited = null
            }
        }
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

    Patient getPatient(Long id) {
       return Patient.get(id)
    }

    void updatePatient(Patient patient) {
        patient.save(flush: true)
    }

    List<MedicalRecord> getMedicalRecordPatient (Patient patient) {
        List<MedicalRecord> listOfMedicalRecords = MedicalRecord.findAllByPatient(patient)
        return listOfMedicalRecords
    }

    List<String> getMedicationsByPatient (Patient patient) {
        List<String> listOfMedications = MedicalRecord.findAllByPatient(patient).collect { it.medicines } as List<String>
        return listOfMedications
    }

    // Make function for query patient's medical number
    List<String> getMedicalNumbersByQuery (String search, Doctor doctor) {
        List<String> listOfMedicalNumbers = Patient.findAllByDoctorAndMedicalNumberIlike(doctor,"%${search}%".toString()).collect(({ patient ->
            [id: patient.id, medicalNumber: patient.medicalNumber]
        } as Closure<String>))
        return listOfMedicalNumbers
    }
}
