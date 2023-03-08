    package medicalrecord.user

    class DoctorService {

        Doctor get(Serializable id){
            return Doctor.findById(id)
        }

        List<Doctor> list(Map args){
            List <Doctor> listOfDoctors = Doctor.findAll(args)
            return listOfDoctors
        }

        Long count() {
            return Doctor.count()
        }

        void delete(Serializable id) {
            Doctor.findById(id).delete()
        }

        Doctor save(Doctor doctor){
            doctor.save(flush: true)
        }

    }