package medicalrecord.appoitment

import medicalrecord.user.Doctor

import javax.servlet.http.HttpServletResponse
import grails.converters.JSON

class MedicalRecordController {
    MedicalRecordService medicalRecordService
    def index() { }
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
            if(doctor.validate()){
                response.message = 'Medical record added to patient successfully'
                response.status = HttpServletResponse.SC_OK
                response.data  = medicalRecord
            }}
        catch (Exception e) {
                response.message = 'Error adding medical record to patient'
                response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                response.error = e.toString()
        }
        render response as JSON
    }
    private Doctor getDoctorIdentity() {
        def token = request.getHeader('Authorization').substring('Bearer '.length())
        def doctorId = Doctor.getIdFromToken(token)
        def doctor = Doctor.get(doctorId)
        return doctor
    }

}
