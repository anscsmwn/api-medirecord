package medicalrecord.appoitment

import medicalrecord.patient.Patient
import medicalrecord.patient.PatientService
import medicalrecord.user.Doctor

import javax.servlet.http.HttpServletResponse
import grails.converters.JSON

class MedicalRecordController {
    MedicalRecordService medicalRecordService
    PatientService patientService
    def index() { }
    def getMedicalRecordsByDoctor() {
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        try {
            Doctor doctor = getDoctorIdentity()
            List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordByDoctor(doctor)
            response.message = 'Medical records retrieved successfully'
            response.status = HttpServletResponse.SC_OK
            response.data = medicalRecords
        } catch (Exception e) {
            response.message = 'Error retrieving medical records'
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.error = e.toString()
        }
        render response as JSON
    }
    def addMedicalRecord() {
        def requestBody = request.JSON
        MedicalRecord medicalRecord = new MedicalRecord(requestBody as Map)
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        try {
            Doctor doctor = getDoctorIdentity()
            medicalRecord.doctor = doctor
            medicalRecordService.addMedicalRecordToPatient(medicalRecord)
            if(medicalRecord.validate()){
                response.message = 'Medical record added to patient successfully'
                response.status = HttpServletResponse.SC_OK
                response.data  = medicalRecord
            }else{
                response.message = 'Medical record not added to patient'
                response.status = HttpServletResponse.SC_BAD_REQUEST
                response.errors = medicalRecord.errors
            }}
        catch (Exception e) {
                response.message = 'Error adding medical record to patient'
                response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                response.error = e.toString()
        }
        render response as JSON
    }
    def getMedicalRecord(Long id) {
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        try {
            MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(id)
            Patient patient = patientService.getPatient(medicalRecord.patient.id)
            response.message = 'Medical record retrieved successfully'
            response.status = HttpServletResponse.SC_OK
            response.data = [medicalRecord: medicalRecord, patient: patient]
            render (response as JSON, status: HttpServletResponse.SC_OK)
        } catch (Exception e) {
            response.message = 'Error retrieving medical record'
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.error = e.toString()
            render (response as JSON, status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
        }
    }
    def updateMedicalRecord(Long id) {
        def requestBody = request.JSON
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord(id)
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        try {
            medicalRecord.setProperties(requestBody as Map)
            medicalRecordService.updateMedicalRecord(medicalRecord)
            if(medicalRecord.validate()){
                response.message = 'Medical record updated successfully'
                response.status = HttpServletResponse.SC_OK
                response.data  = medicalRecord
            }else{
                response.message = 'Medical record not updated'
                response.status = HttpServletResponse.SC_BAD_REQUEST
                response.errors = medicalRecord.errors
            }}
        catch (Exception e) {
            response.message = 'Error updating medical record'
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.error = e.toString()
        }
        render response as JSON
    }
    def deleteMedicalRecord(Long id) {
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        try {
            medicalRecordService.deleteMedicalRecord(id)
            response.message = 'Medical record deleted successfully'
            response.status = HttpServletResponse.SC_OK
        } catch (Exception e) {
            response.message = 'Error deleting medical record'
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.error = e.toString()
        }
    }
    private Doctor getDoctorIdentity() {
        def token = request.getHeader('Authorization').substring('Bearer '.length())
        def doctorId = Doctor.getIdFromToken(token)
        def doctor = Doctor.get(doctorId)
        return doctor
    }

}
