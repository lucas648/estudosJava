package com.lucascosta.cursomc.domain.enums;

/*
 * enum para vinculação do tipo de cliente, sendo eles
 * 'PESSOAFISICA' e 'PESSOAJURIDICA', colocando o numero e atribuindo nome
 * para cada um deles 
 */
public enum TipoCliente {
	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descrição;
	
	private TipoCliente(int cor, String descrição) {
		this.cod = cod;
		this.descrição = descrição;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescrição() {
		return descrição;
	}
   
	public static TipoCliente toEnum( Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for(TipoCliente x : TipoCliente.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
