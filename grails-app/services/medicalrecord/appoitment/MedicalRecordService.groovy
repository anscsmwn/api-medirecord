package medicalrecord.appoitment

import grails.gorm.transactions.Transactional
import medicalrecord.patient.Patient
import medicalrecord.user.Doctor
import org.hibernate.Hibernate

import java.time.LocalDateTime

@Transactional
class MedicalRecordService {

    MedicalRecord addMedicalRecordToPatient(MedicalRecord medicalRecord) {
        medicalRecord.createdAt = LocalDateTime.now().toString()
        medicalRecord.save(flush: true)
    }
    List<MedicalRecord> getMedicalRecordByDoctor(Doctor doctor){
        List<MedicalRecord> listOfMedicalRecords = MedicalRecord.findAllByDoctor(doctor)
        return listOfMedicalRecords
    }
    MedicalRecord getMedicalRecord(Long id){
        MedicalRecord detailMedicalRecord = MedicalRecord.get(id)
        return detailMedicalRecord
    }
    void deleteMedicalRecord(Long id){
        MedicalRecord medicalRecord = MedicalRecord.get(id)
        medicalRecord.delete()
    }
    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecord.save(flush: true)
    }
}
