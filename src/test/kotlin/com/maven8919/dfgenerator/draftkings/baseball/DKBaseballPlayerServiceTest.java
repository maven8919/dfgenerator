package com.maven8919.dfgenerator.draftkings.baseball;


import com.maven8919.dfgenerator.draftkings.baseball.service.DKBaseballPlayerService;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DKBaseballPlayerServiceTest {

    @Autowired private DKBaseballPlayerService dkBaseballPlayerService;

    @Test
    public void getPlayersShouldReturnXNumberOfPlayersGivenSampleCsv() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("DKSalaries.csv").getFile());
        InputStream inStream = new FileInputStream(file);
        byte[] content = IOUtils.toByteArray(inStream);
        MultipartFile multipartFile = new MockMultipartFile("DkSalaries.csv", content);
        assertEquals(274, dkBaseballPlayerService.getPlayers(multipartFile).size());
    }

}
