package pt.claiver.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterDados {
    private ObjectMapper mapper = new ObjectMapper();

    //Implementar o override de IConverteDados
        public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter dados: " + e.getMessage(), e);
        }
    }
}
