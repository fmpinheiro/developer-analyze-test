package br.com.segware;

import br.com.helper.MapComparator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe que implementa os metodos propostos em IAnalisadorRelatorio.
 *
 * @author Deivide
 */
public class AnalisadorRelatorio implements IAnalisadorRelatorio {

    public enum ESTRUTURA {

        COD_SEQUENCIAL(0), COD_CLIENTE(1), COD_EVENTO(2), TIPO_EVENTO(3), DT_INICIO(
                4), DT_FIM(5), COD_ATENDENTE(6);

        public int value;

        private ESTRUTURA(int value) {
            this.value = value;
        }
    }
    public static final String FILEPATH = "src/test/java/br/com/segware/relatorio.csv";

    private BufferedReader bufReader;

    public AnalisadorRelatorio() {
        try {
            this.bufReader = new BufferedReader(new FileReader(new File(FILEPATH)));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Map<String, Integer> getTotalEventosCliente() {
        return this.getTotalEventosClienteImpl();
    }

    @Override
    public Map<String, Long> getTempoMedioAtendimentoAtendente() {
        return this.getTempoMedioAtendimentoAtendenteImpl();
    }

    @Override
    public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
        return this.getTiposOrdenadosNumerosEventosDecrescenteImpl();
    }

    @Override
    public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
        return this.getCodigoSequencialEventosDesarmeAposAlarmeImp();
    }

    /**
     * Calcula o total de eventos por cliente. Esse método não necessita de
     * banco de dados, ele realiza as operações diretamente dos dados do
     * arquivo.
     *
     * @return total de eventos por cliente
     */
    public Map<String, Integer> getTotalEventosClienteImpl() {
        Map<String, Integer> map = new HashMap<>();
        try {
            String line;
            while ((line = bufReader.readLine()) != null) {
                String parts[] = line.split(",");
                if ((Integer) (map.get(parts[ESTRUTURA.COD_CLIENTE.value])) == null) {
                    map.put(parts[ESTRUTURA.COD_CLIENTE.value], 1);
                } else {
                    Integer value = (Integer) (map
                            .get(parts[ESTRUTURA.COD_CLIENTE.value])) + 1;
                    map.put(parts[ESTRUTURA.COD_CLIENTE.value], value);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    /**
     * Calcula o tempo medio de atendimento por atendente. Esse método não
     * necessita de banco de dados, ele realiza as operações diretamente dos
     * dados do arquivo.
     *
     * @return o tempo medio de atendimento por atendente
     */
    public Map<String, Long> getTempoMedioAtendimentoAtendenteImpl() {
        Map<String, Long[]> map2 = new HashMap<>();
        Map<String, Long> result = new HashMap<>();
        try {
            String line;
            while ((line = bufReader.readLine()) != null) {
                String parts[] = line.split(",");
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String dtInicio = parts[ESTRUTURA.DT_INICIO.value];
                String dtFim = parts[ESTRUTURA.DT_FIM.value];
                Date data1 = format.parse(dtInicio.replace('-', '/'));
                Date data2 = format.parse(dtFim.replace('-', '/'));
                long seconds = this.timeDiffSeconds(data2, data1);
                if (map2.get(parts[ESTRUTURA.COD_ATENDENTE.value]) == null) {
                    Long[] value = {1L, seconds};
                    map2.put(parts[ESTRUTURA.COD_ATENDENTE.value], value);
                } else {
                    Long[] newValue = map2.get(parts[ESTRUTURA.COD_ATENDENTE.value]);
                    newValue[0]++;
                    newValue[1] += seconds;
                    map2.put(parts[ESTRUTURA.COD_ATENDENTE.value], newValue);
                }
            }
            for (String string : map2.keySet()) {
                Long avg = map2.get(string)[1] / map2.get(string)[0];
                result.put(string, avg);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParseException parse) {
            System.out.println(parse.getMessage());
        }
        return result;
    }

    
    /**
     * 
     * @return lista de tipos ordenada em ordem decrescente 
     */
    public List<Tipo> getTiposOrdenadosNumerosEventosDecrescenteImpl() {
        Map<String, Integer> map = new HashMap<>();
        List<Tipo> resultado = new ArrayList<>();

        try {
            String line;
            while ((line = bufReader.readLine()) != null) {
                String parts[] = line.split(",");
                if ((Integer) (map.get(parts[ESTRUTURA.TIPO_EVENTO.value])) == null) {
                    map.put(parts[ESTRUTURA.TIPO_EVENTO.value], 1);
                } else {
                    Integer value = (Integer) (map
                            .get(parts[ESTRUTURA.TIPO_EVENTO.value])) + 1;
                    map.put(parts[ESTRUTURA.TIPO_EVENTO.value], value);
                }
            }
            MapComparator mapComparator = new MapComparator(map);
            TreeMap<String, Integer> treeMap = new TreeMap<>(mapComparator);
            treeMap.putAll(map);
            for (String string : treeMap.keySet()) {
                resultado.add(Tipo.valueOf(string));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return resultado;
    }

    /**
     *
     * @return lista sequencial de eventos de desarme apos alarme
     */
    public List<Integer> getCodigoSequencialEventosDesarmeAposAlarmeImp() {
        List<Integer> resultado = new ArrayList<>();
        List<Ocorrencia> aux = new ArrayList<>();
        try {
            String line;
            while ((line = bufReader.readLine()) != null) {
                String parts[] = line.split(",");
                Ocorrencia ocorrencia = new Ocorrencia(parts);

                if (ocorrencia.getTipoEvento().compareTo("ALARME") == 0) {
                    aux.add(ocorrencia);
                } else {
                    Ocorrencia ocorrenciaAlarme = this.possuiIdCliente(aux, ocorrencia.getIdCliente().getId());
                    if (ocorrencia.getTipoEvento().compareTo("DESARME") == 0
                            && ocorrenciaAlarme != null) {
                        //verifica a diferenca de tempo
                        long res = this.timeDiffSeconds(ocorrencia.getDtInicio(), ocorrenciaAlarme.getDtInicio());
                        //300 segundos = 5 minutos
                        if (res <= 300) {
                            resultado.add(ocorrencia.getId());
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    /**
     * Calcula a diferenca entre duas datas
     * @param time1 representa a data de inicio do desarme
     * @param time2 representa a data de inicio do alarme
     * @return a diferenca entre as datas
     */
    public Long timeDiffSeconds(Date time1, Date time2) {
        long seconds;
        long diferenca = time1.getTime() - time2.getTime();
        seconds = diferenca / 1000;
        return new Long(seconds);
    }

    /**
     * Verifica se a lista possui o id do cliente, ou seja,
     * se o id_cliente do desarme é o mesmo que o do alarme.
     * @param list representa a lista de alarmes armazenados ate entao
     * @param id representa o id do desarme
     * @return se encontrar retorna uma ocorrencia, senao retorna null
     */
    public Ocorrencia possuiIdCliente(List<Ocorrencia> list, String id) {
        for (Ocorrencia ocorrencia : list) {
            if (ocorrencia.getIdCliente().getId().compareTo(id) == 0) {
                return ocorrencia;
            }
        }
        return null;
    }


}
