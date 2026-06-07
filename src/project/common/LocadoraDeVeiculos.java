package project.common;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.util.HashMap;

public class LocadoraDeVeiculos implements Locacao{
	private Map<String,ArrayList<MeiosDeTransporte>> disponiveis;
	private ArrayList<MeiosDeTransporte> alugados;
	
	public LocadoraDeVeiculos(){
		disponiveis = new HashMap<>();
		alugados = new ArrayList<>();
		
		ArrayList<MeiosDeTransporte> carros = new ArrayList<>();
		disponiveis.put("carro", carros);
		
		ArrayList<MeiosDeTransporte> motos = new ArrayList<>();
		disponiveis.put("moto", motos);
		
		ArrayList<MeiosDeTransporte> onibus = new ArrayList<>();
		disponiveis.put("onibus", onibus);
		
		ArrayList<MeiosDeTransporte> caminhao = new ArrayList<>();
		disponiveis.put("caminhao", caminhao);

        this.novoVeiculo(new Moto("Honda", "cgFan160", "Vermelha", "BL4CK"));
        this.novoVeiculo(new Moto("Honda", "NRX160", "Preta", "D4RT2"));
        this.novoVeiculo(new Moto("Yamaha", "xj6", "Azul", "M4D4R4"));
        
		this.novoVeiculo(new CarroDePasseio("Subaru", "ImprezaWRX", "Azul", "L1GTH"));
        this.novoVeiculo(new CarroDePasseio("Wolkswagen", "Golf", "Branco", "J0H4N"));
        this.novoVeiculo(new CarroDePasseio("Honda", "Civic", "Prata", "M4R1K"));
        this.novoVeiculo(new CarroDePasseio("Chevrolet", "Camaro", "Amarelo", "ER3N"));
        
		this.novoVeiculo(new Onibus("Wolskwagem", "Scolarship", "Amarelo", "5CH00L"));
        
		this.novoVeiculo(new Caminhao("Mercedes", "Axor", "Prata", "154BEL4"));
        this.novoVeiculo(new Caminhao("Ford", "Torqshift", "Prata", "D4K1"));
	}
	
	public void novoVeiculo(MeiosDeTransporte v) {
		String tipo = v.getTipo();
		
		switch(tipo){
			case "carro":
				disponiveis.get("carro").add(v);
				break;
			case "caminhao":
				disponiveis.get("caminhao").add(v);
				break;
			case "onibus":
				disponiveis.get("onibus").add(v);
				break;
			case "moto":
				disponiveis.get("moto").add(v);
				break;
			default:
				System.out.println("Tipo de veiculo não identificado");
				break;
		}
	}

	public MeiosDeTransporte Alugar(String s){
		int index = Integer.parseUnsignedInt(s);
		int adjust = 0;
		Boolean flag = false;

		adjust = index - this.disponiveis.get("carro").size();

		if(adjust < 0 && !flag){
			getVeiculo(this.disponiveis.get("carro").get(index - 1));
			flag = true;
		}
		else{
			if(adjust == 0 && !flag){
				getVeiculo(this.disponiveis.get("carro").get(adjust));
				flag = true;
			}
		}

		adjust = index - (this.disponiveis.get("carro").size() + this.disponiveis.get("moto").size());

		if(adjust < 0 && !flag){
			adjust = index - this.disponiveis.get("carro").size();
			getVeiculo(this.disponiveis.get("moto").get(adjust - 1));
			flag = true;
		}
		else{
			if(adjust == 0 && !flag){
				getVeiculo(this.disponiveis.get("moto").get(adjust));
				flag = true;
			}
		}

		adjust = index - (this.disponiveis.get("carro").size() + this.disponiveis.get("moto").size() + this.disponiveis.get("caminhao").size());
		if(adjust < 0 && !flag){
			adjust = index - (this.disponiveis.get("carro").size() + this.disponiveis.get("moto").size());
			getVeiculo(this.disponiveis.get("caminhao").get(adjust - 1));
			flag = true;
		}
		else{
			if(adjust == 0 && !flag){
				getVeiculo(this.disponiveis.get("caminhao").get(adjust));
				flag = true;
			}
		}

		adjust = index - (this.disponiveis.get("carro").size() + this.disponiveis.get("moto").size() + this.disponiveis.get("caminhao").size() + this.disponiveis.get("onibus").size());
		if(adjust < 0 && !flag){
			adjust = index - (this.disponiveis.get("carro").size() + this.disponiveis.get("moto").size() + this.disponiveis.get("caminhao").size());
			getVeiculo(this.disponiveis.get("onibu").get(adjust - 1));
			flag = true;
		}
		else{
			if(adjust == 0 && !flag){
				getVeiculo(this.disponiveis.get("onibus").get(adjust));
				flag = true;
			}
		}

		return this.alugados.getLast();
	}
	
	private void getVeiculo(MeiosDeTransporte v){
		String tipo = v.getTipo();
		boolean aux = true;
		
		for(int i = 0; i < disponiveis.get(tipo).size() && aux; i++) {
			if(disponiveis.get(tipo).get(i).getModelo().equals(v.getModelo())) {
				alugados.add(disponiveis.get(tipo).get(i));
				disponiveis.get(tipo).remove(i);
				aux = false;
			}
		}
		
		if(aux) {
			System.out.println(String.format("O modelo %s não está disponivel",v.getModelo()));
		}
	}
	
	public MeiosDeTransporte Devolver(String s){
		boolean aux = true;
		MeiosDeTransporte veiculoDevolvido = disponiveis.get("carro").get(0);
		
		for(int i = 0; i < this.alugados.size() && aux; i++){
			if(alugados.get(i).getModelo().equals(s)){
				veiculoDevolvido = alugados.get(i);
				disponiveis.get(alugados.get(i).getTipo()).add(alugados.get(i));
				alugados.remove(i);
				aux = false;
			}
		}

		if(aux) {
			System.out.println(String.format("O veiculo de modelo %s não foi alugado, você cometeu um engano.", s));
		}

		return veiculoDevolvido;
	}

	public void Devolver(MeiosDeTransporte v){
		String tipo = v.getTipo();
		boolean aux = true;
		
		for(int i = 0; i < alugados.size() && aux; i++) {
			if(v.getModelo().equals(alugados.get(i).getModelo())) {
				disponiveis.get(tipo).add(v);
				alugados.remove(i);
				aux = false;
			}
		}
		
		if(aux) {
			System.out.println(String.format("O veiculo de modelo %s não foi alugado, você cometeu um engano.", v.getModelo()));
		}
	}
	
	public void venderVeiculo(MeiosDeTransporte v) {
		String tipo = v.getTipo();
		boolean aux = true;
		
		for(int i = 0; i < disponiveis.get(tipo).size() && aux; i++) {
			if(disponiveis.get(tipo).get(i).getModelo().equals(v.getModelo())){
				disponiveis.get(tipo).remove(i);
				aux = false;
			}
		}
		
		if(aux) {
			System.out.println(String.format("O veículo de modelo %s que você está tentando vender já foi alugado. Por favor tente novamente mais tarde.", v.getModelo()));
		}
	}
	
	public String listarDisponiveis(){
		String text = "";
		int index = 1;

		text += "Veiculos disponiveis\n";
		text += "CARROS\n";
		for(int i = 0; i < disponiveis.get("carro").size(); i++) {
			text += String.valueOf(index) + ". ";
			text += disponiveis.get("carro").get(i).toString();
			index += 1;
		}
		text += "MOTOS\n";
		for(int i = 0; i < disponiveis.get("moto").size(); i++) {
			text += String.valueOf(index) + ". ";
			text += disponiveis.get("moto").get(i).toString();
			index += 1;
		}
		text += "CAMINHÕES\n";
		for(int i = 0; i < disponiveis.get("caminhao").size(); i++) {
			text += String.valueOf(index) + ". ";
			text += disponiveis.get("caminhao").get(i).toString();
			index += 1;
		}
		text += "ÔNIBUS\n";
		for(int i = 0; i < disponiveis.get("onibus").size(); i++) {
			text += String.valueOf(index) + ". ";
			text += disponiveis.get("onibus").get(i).toString();
			text += 1;
		}

		return text;
	}

	public String toString(){
		String text = "";
		
		text += "Veiculos disponiveis\n";
		text += "CARROS\n";
		for(int i = 0; i < disponiveis.get("carro").size(); i++) {
			text += disponiveis.get("carro").get(i).toString();
		}
		text += "MOTOS\n";
		for(int i = 0; i < disponiveis.get("moto").size(); i++) {
			text += disponiveis.get("moto").get(i).toString();
		}
		text += "CAMINHOES\n";
		for(int i = 0; i < disponiveis.get("caminhao").size(); i++) {
			text += disponiveis.get("caminhao").get(i).toString();
		}
		text += "ONIBUS\n";
		for(int i = 0; i < disponiveis.get("onibus").size(); i++) {
			text += disponiveis.get("onibus").get(i).toString();
		}
		
		text += "Veiculos alugados\n";
		for(int i = 0; i < alugados.size(); i++) {
			text += alugados.get(i).toString();
		}
		
		return text;
	}
}
