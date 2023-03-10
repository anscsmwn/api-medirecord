package medicalrecord.patient
import grails.converters.JSON
import medicalrecord.user.Doctor
import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletResponse

class PatientController {
    PatientService patientService

    def index() { }
    def addPatientToDoctor() {
        def requestBody = request.JSON
        Patient patient = new Patient(requestBody as Map)
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        try {
            Doctor doctor = getDoctorIdentity()
            patientService.addPatientToDoctor(patient, doctor)
            if(patient.validate()){
                response.message = 'Patient added to doctor successfully'
                response.status = HttpServletResponse.SC_OK
                response.data  = patient
            }else{
                response.message = 'Patient not added to doctor'
                response.status = HttpServletResponse.SC_BAD_REQUEST
                response.errors = patient.errors
                render (response as JSON , status: HttpServletResponse.SC_BAD_REQUEST)
                return
            }
            render response as JSON
        } catch (Exception e) {
            response.message = 'Error adding patient to doctor'
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.error = e.toString()
            render (response as JSON, status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
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
        response.data = patients
        render response as JSON
    }
    def getPatientById() {
        String patientId = params.id
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        try{
            def patient = patientService.getPatientById(patientId)
            if(!patient){
                response.message = 'Patient not found'
                response.status = HttpServletResponse.SC_NOT_FOUND
                render response as JSON, status: HttpServletResponse.SC_NOT_FOUND
                return
            }
            response.message = 'Patient retrieved successfully'
            response.status = HttpServletResponse.SC_OK
            response.data = patient
            render response as JSON
        }   catch (Exception e) {
            response.message = 'Error retrieving patient'
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.error = e.toString()
            render response as JSON, status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        }

    }
    def updatePatient(Long id) {
        def requestBody = request.JSON
        Doctor doctor = getDoctorIdentity()
        def patient = patientService.getPatient(id)
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        if(!patient){
            response.message = 'Patient not found'
            response.status = HttpServletResponse.SC_NOT_FOUND
            render response as JSON, status: HttpServletResponse.SC_NOT_FOUND
            return
        }
        def dateFormat = new SimpleDateFormat("yyyy-MM-dd")
        Date birthDate = dateFormat.parse(requestBody.birthDate)
        patient.name = requestBody.name
        patient.address = requestBody.address
        patient.phone = requestBody.phone
        patient.birthDate =  birthDate
        patient.doctor = doctor
        patientService.updatePatient(patient)
        if (patient.validate()) {
            response.message = 'Patient updated successfully'
            response.status = HttpServletResponse.SC_OK
        } else {
            response.message = 'Patient not updated'
            response.status = HttpServletResponse.SC_BAD_REQUEST
            response.errors = patient.errors
            render(response as JSON , status: HttpServletResponse.SC_BAD_REQUEST)
            return
        }
        render(response as JSON, status: HttpServletResponse.SC_OK)
    }
    def deletePatient(Long id){
        Patient patient = patientService.getPatient(id)
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        if(!patient){
            response.message = 'Patient not found'
            response.status = HttpServletResponse.SC_NOT_FOUND
            render response as JSON, status: HttpServletResponse.SC_NOT_FOUND
            return
        }
        patientService.deletePatient(params)
        response.message = 'Patient deleted successfully'
        response.status = HttpServletResponse.SC_OK
        render response as JSON
    }
    def getMedicalNumberPatients() {
        def response = [:]
        response.endpoint = request.requestURI
        response.method = request.method
        try {
        def medicalNumbers = patientService.getAllPatientsMedicalNumbers()
        response.message =  'Medical records retrieved successfully'
        response.status = HttpServletResponse.SC_OK
        response.data = medicalNumbers
        render response as JSON
        } catch (Exception e) {
            response.message = 'Error retrieving medical records'
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.error = e.toString()
            render response as JSON, status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        }
    }

    private Doctor getDoctorIdentity() {
        def token = request.getHeader('Authorization').substring('Bearer '.length())
        def doctorId = Doctor.getIdFromToken(token)
        def doctor = Doctor.get(doctorId)
        return doctor
    }
}
