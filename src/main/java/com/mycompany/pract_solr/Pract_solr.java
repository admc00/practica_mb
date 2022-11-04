/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.pract_solr;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author antonio diego
 */
public class Pract_solr {

    public static void main(String[] args) throws SolrServerException, IOException {
        final SolrClient cliente = new HttpSolrClient.Builder("http://localhost:8983/solr").build();
        String fileName = "C:\\Users\\antonio diego\\Documents\\UNI\\MB\\CISI\\CISI.ALL";
        Scanner scan = new Scanner(new File(fileName));
        SolrInputDocument doc = new SolrInputDocument();
        String line = "";
        String titulo = "";
        String texto = "";
        while (scan.hasNextLine()) {
            line = scan.nextLine();

            if (line.contains(".I")) {
                String indice = "";
                indice = line.substring(3);
                doc.addField("index", indice);
            }

            if (line.contains(".T")) {

                while (!line.contains(".A")) {
                    line = scan.nextLine();
                    if (!line.contains(".A")) {
                        titulo += " " + line;
                    } else {
                        doc.addField("title", titulo);
                        titulo = "";
                    }
                }
            }
            if (line.contains(".A")) {
                while (!line.equals(".W")) {
                    line = scan.nextLine();
                    if (!line.equals(".W")) {
                        doc.addField("author", line);
                    }
                }
            }
            if (line.contains(".W")) {
                while (!line.contains(".X")) {
                    line = scan.nextLine();
                    if (!line.contains(".X")) {
                        texto += line + " ";
                    } else {
                        doc.addField("text", texto);
                        texto = "";
                        final UpdateResponse updateResponse = cliente.add("pruebas3", doc);
                        doc = new SolrInputDocument();
                    }
                }
            }
        }
        cliente.commit("pruebas3");
    }
}
