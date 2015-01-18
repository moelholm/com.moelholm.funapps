package hello.web;

import hello.StringResultHelper;
import hello.service.FileService;
import hello.service.SoundService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RootController {

    private static final String EXTENSION_WAV = ".wav";

    @Autowired
    private SoundService soundService;

    @Autowired
    private FileService fileService;

    @Autowired
    private StringResultHelper resultHelper;

    @ResponseBody
    @RequestMapping("/")
    public String home() {
        StringBuilder usage = new StringBuilder();
        usage.append("Usage: http://[host]:[port]/[command].\n\n");
        usage.append("  Where [command] is one of:\n\n");
        usage.append("  listsounds             : GET  : list all sound files\n");
        usage.append("  playsound/[clip]       : GET  : play a sound\n");
        usage.append("  uploadfile             : POST : upload a file as multipart/form-data ( use propertyname 'file' )\n");
        return usage.toString();
    }

    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping("/listsounds")
    public @ResponseBody String listSounds() {
        return fileService.listFileNamesUsingCommaSeparatedList(".wav");
    }

    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file) {

        String name = file.getOriginalFilename();

        if (!name.endsWith(EXTENSION_WAV)) {
            return resultHelper.toErrorResponse(String.format("Not a .wav file: %s", name));
        }

        return fileService.save(name, file);
    }

    @RequestMapping("/playsound/{clip}")
    public @ResponseBody String whaaat(@PathVariable("clip") String clip) {

        if (!clip.endsWith(EXTENSION_WAV)) {
            clip = clip + EXTENSION_WAV;
        }

        return soundService.playSound(clip);
    }
}
