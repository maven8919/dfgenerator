package com.maven8919.dfgenerator.draftkings.baseball;


import com.maven8919.dfgenerator.draftkings.baseball.service.DKBaseballPlayerService;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DKBaseballPlayerServiceTest {

    @Autowired private DKBaseballPlayerService dkBaseballPlayerService;
    private MultipartFile multipartFile;

    @Before
    public void setUp() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("DKSalaries.csv").getFile());
        InputStream inStream = new FileInputStream(file);
        byte[] content = IOUtils.toByteArray(inStream);
        multipartFile = new MockMultipartFile("DkSalaries.csv", content);
    }

    @Test
    public void testGetPlayersShouldReturn274NumberOfPlayersGivenSampleCsv() throws IOException {
        assertEquals(274, dkBaseballPlayerService.getPlayers(multipartFile).size());
    }

    @Test
    public void testCodyBellingerShouldHave2Positions() {
        DKBaseballPlayer codyBellinger = dkBaseballPlayerService.getPlayers(multipartFile).get(62);
        assertEquals(2, codyBellinger.getPosition().size());
    }

    @Test
    public void testGetStartersShouldReturn10NumberOfPlayersGivenSampleCsv() throws IOException {
        List<DKBaseballPlayer> players = dkBaseballPlayerService.getPlayers(multipartFile);
        List<DKBaseballPlayer> starters = dkBaseballPlayerService.generateStarters(players);
        assertEquals(10, starters.size());
    }

}
