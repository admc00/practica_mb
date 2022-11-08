/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pract_solr;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author antonio diego
 */
public class Query {

    public static void main(String[] args) throws IOException, SolrServerException {
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/pruebas3").build();
        String fileName = "C:\\Users\\antonio diego\\OneDrive - UNIVERSIDAD DE HUELVA\\Documentos\\UNI\\MB\\CISI\\CISI.QRY";
        Scanner scan = new Scanner(new File(fileName));
        SolrQuery query = new SolrQuery();
        String consulta = "";
        while (scan.hasNextLine()) {
            String palabra = scan.nextLine();
            if (palabra.equals(".W")) {
                for (int i = 0; i < 5; i++) {
                    palabra = scan.next();
                    if(palabra.startsWith("(")){
                        palabra = palabra.substring(1);
                    }
                    if(palabra.endsWith(")")){
                        palabra = palabra.substring(1,palabra.length()-1);
                    }
                    consulta += palabra + " ";
                }
                //System.out.println(consulta);
                query.setQuery("text:" + consulta);
                QueryResponse rsp = solr.query(query);
                SolrDocumentList docs = rsp.getResults();
                for (int i = 0; i < docs.size(); ++i) {
                    System.out.println(docs.get(i));
                }
                consulta = "";
            }
        }
    }
}
