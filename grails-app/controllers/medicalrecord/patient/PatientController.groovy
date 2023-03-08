package medicalrecord.patient
import grails.converters.JSON
import medicalrecord.user.Doctor

import javax.servlet.http.HttpServletResponse

class PatientController {
    PatientService patientService

    def index() { }
    def addPatientToDoctor() {
        def requestBody = request.JSON
        Patient patient = new Patient(requestBody as Map)
        def token = request.getHeader('Authorization').substring('Bearer '.length())

        try {
            def doctorId = Doctor.getIdFromToken(token)
            def doctor = Doctor.get(doctorId)
            patientService.addPatientToDoctor(patient, doctor)
            def response = [:]
            response.endpoint = request.requestURI
            response.method = request.method
            response.message = 'Patient added to doctor successfully'
            response.status = HttpServletResponse.SC_OK

            render response as JSON
        } catch (Exception e) {
            def response = [:]
            response.message = 'Error adding patient to doctor'
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.error = e.toString()

            render response as JSON
        }
    }
    def getPatientsByDoctor() {
        def token = request.getHeader('Authorization').substring('Bearer '.length())
        def doctorId = Doctor.getIdFromToken(token)
        def doctor = Doctor.get(doctorId)
        def patients = patientService.getPatientsByDoctor(doctor)
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        response.message = 'Patients retrieved successfully'
        response.status = HttpServletResponse.SC_OK
        response.patients = patients

        render response as JSON
    }
}
