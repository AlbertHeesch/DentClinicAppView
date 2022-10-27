package com.dentclinic.view.dentclinic_view.mapper;

import com.dentclinic.view.dentclinic_view.domain.Dentist;
import com.dentclinic.view.dentclinic_view.domain.DentistDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DentistMapper
{
    public Dentist mapToDentist(final DentistDto dentistDto) {
        return new Dentist(
                dentistDto.getId(),
                dentistDto.getName(),
                dentistDto.getSurname(),
                dentistDto.getExperience()
        );
    }

    public DentistDto mapToDentistDto(final Dentist dentist)
    {
        return new DentistDto(
                dentist.getId(),
                dentist.getName(),
                dentist.getSurname(),
                dentist.getExperience()
        );
    }

    public List<Dentist> mapToDentistList(final List<DentistDto> dentistDtoList)
    {
        return dentistDtoList.stream()
                .map(this::mapToDentist)
                .collect(Collectors.toList());
    }
}
