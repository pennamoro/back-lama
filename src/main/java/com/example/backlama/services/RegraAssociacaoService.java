package com.example.backlama.services;

import com.example.backlama.dto.RegrasAssociacaoDTO;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RegraAssociacaoService {

    public List<RegrasAssociacaoDTO> lerRegrasDeAssociacao(String path) {
        List<RegrasAssociacaoDTO> regras = new ArrayList<>();
        boolean firstLineSkipped = false;

        try {
            FileReader fileReader = new FileReader(ResourceUtils.getFile(path));
            CSVReader csvReader = new CSVReader(fileReader);
            String[] line;

            while ((line = csvReader.readNext()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }

                List<String> antecedents = parseList(line[0]);
                List<String> consequents = parseList(line[1]);
                double confidence = Double.parseDouble(line[2]);
                double lift = Double.parseDouble(line[3]);

                RegrasAssociacaoDTO regra = new RegrasAssociacaoDTO();
                regra.setAntecedents(antecedents);
                regra.setConsequents(consequents);
                regra.setConfidence(confidence);
                regra.setLift(lift);

                regras.add(regra);
            }
        } catch (Exception ignored) {
        }
        return regras;
    }

    private List<String> parseList(String input) {
        input = input.replaceAll("[\\[\\]']", "").trim();
        String[] elements = input.split(", ");
        return Arrays.asList(elements);
    }
}
