/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.marcosinocencio.cursomc.services.exceptions.FileException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Vinicius
 */
@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile) {

        try {
            String filename = multipartFile.getOriginalFilename();
            InputStream is = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(is, filename, contentType);
        } catch (IOException ex) {
            throw new FileException("Erro de IO: " + ex.getMessage());
        }

    }

    public URI uploadFile(InputStream is, String filename, String contentType) {
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);

            LOG.info("Iniciando upload");
            s3client.putObject(bucketName, filename, is, meta);
            LOG.info("Upload finalizado");
            return s3client.getUrl(bucketName, filename).toURI();
        } catch (URISyntaxException ex) {
            throw new FileException("Erro ao converter URL to URI");
        }
    }
}
