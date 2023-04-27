package com.dentclinic.view.dentclinic_view.mapper;

import com.dentclinic.view.dentclinic_view.domain.Clinic;
import com.dentclinic.view.dentclinic_view.domain.ClinicDto;
import org.springframework.stereotype.Service;

@Service
public class ClinicMapper {
    private Clinic mapToClinic(final ClinicDto clinicDto) {
        return new Clinic(
                clinicDto.getId(),
                clinicDto.getName(),
                clinicDto.getAddress(),
                clinicDto.getDentists()
        );
    }

    private ClinicDto mapToClinicDto(final Clinic clinic) {
        return new ClinicDto(
                clinic.getId(),
                clinic.getName(),
                clinic.getAddress(),
                clinic.getDentists()
        );
    }
}