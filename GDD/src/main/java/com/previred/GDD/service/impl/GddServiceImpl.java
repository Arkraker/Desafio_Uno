package com.previred.GDD.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.previred.GDD.model.GddRequest;
import com.previred.GDD.model.GddResponse;
import com.previred.GDD.service.GddService;
import com.previred.GDD.tools.FechasFaltantes;

@Service
public class GddServiceImpl implements GddService {
	
	@Override
	public GddResponse getPeriodos(GddRequest request) {
		GddResponse response = new GddResponse();
		Set<LocalDate> fechas = new HashSet<>();
		FechasFaltantes fechasFaltantes = new FechasFaltantes(request.getFechaCreacion(), request.getFechaFin());

		//Se asignan los datos principales del request hacia el response
		response.setId(request.getId());
		response.setFechaCreacion(request.getFechaCreacion());
		response.setFechaFin(request.getFechaFin());
		response.setFechas(request.getFechas());
		
		//Se generan las fechas faltantes
		Random random = new Random();
		int fechasEntrantes = request.getFechas().size();
		int cantidadFechas = random.nextInt(fechasFaltantes.totalDates() + 2 - fechasEntrantes) + (fechasEntrantes - 3);
		System.out.println(cantidadFechas);
		while(fechas.size() < cantidadFechas) {
			fechas.add(fechasFaltantes.nextDate());
			fechas.removeIf(x -> request.getFechas().contains(x));
		}
		
		//Ordena fechas entrantes
		response.setFechasFaltantes(fechas.stream().sorted().collect(Collectors.toList()));
		
		return response;
	}
		
}
