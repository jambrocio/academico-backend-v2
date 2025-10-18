package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import pe.edu.university.dto.PagoDto;
import pe.edu.university.entity.Pago;

@Component
public class PagoMapper {
    public PagoDto toDto(Pago e){
        if(e==null) return null;
        return PagoDto.builder()
                .pagoId(e.getPagoId())
                .matriculaId(e.getMatriculaId())
                .fechaPago(e.getFechaPago())
                .monto(e.getMonto())
                .metodoPago(e.getMetodoPago())
                .referencia(e.getReferencia())
                .estado(e.getEstado())
                .build();
    }
    public Pago toEntity(PagoDto d){
        if(d==null) return null;
        Pago e = new Pago();
        e.setFechaPago(d.getFechaPago());
        e.setMonto(d.getMonto());
        e.setMetodoPago(d.getMetodoPago());
        e.setReferencia(d.getReferencia());
        e.setEstado(d.getEstado());
        return e;
    }
}
