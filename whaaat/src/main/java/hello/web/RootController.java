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

    @RequestMapping("/")
    public @ResponseBody String home() {
        return help();
    }

    @RequestMapping("/help")
    public @ResponseBody String help() {
        StringBuilder usage = new StringBuilder();
        usage.append("Usage: http://[host]:[port]/[command].\n\n");
        usage.append("  Where [command] is one of:\n\n");
        usage.append("  listsounds             : GET  : list all sound files\n");
        usage.append("  playsound/[file]       : GET  : play a sound\n");
        usage.append("  addsound               : POST : upload a sound file as multipart/form-data ( use propertyname 'file' )\n");
        return usage.toString();

    }

    @RequestMapping("/listsounds")
    public @ResponseBody String listSounds() {
        return fileService.listFileNamesUsingCommaSeparatedList(EXTENSION_WAV);
    }

    @RequestMapping(value = "/addsound", method = RequestMethod.POST)
    public @ResponseBody String addSound(@RequestParam("file") MultipartFile file) {

        String name = file.getOriginalFilename();

        if (!name.endsWith(EXTENSION_WAV)) {
            return resultHelper.errorResponse(String.format("Not a %s file: %s", EXTENSION_WAV, name));
        }

        return fileService.save(name, file);
    }

    @RequestMapping("/playsound/{sound}")
    public @ResponseBody String playSound(@PathVariable("sound") String sound) {

        if (!sound.endsWith(EXTENSION_WAV)) {
            sound = sound + EXTENSION_WAV;
        }

        return soundService.playSound(sound);
    }
}
