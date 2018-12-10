package vinkkeri.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TagMap {

    private HashMap<String, ArrayList<String>> tags;

    public TagMap() {
        this.tags = new HashMap<>();
        this.init();
    }

    private void init() {
        final String domainYoutube = "youtube.com";
        final String domainYoutubeShort = "youtu.be";
        final ArrayList<String> youtubeTags = new ArrayList<>();
        youtubeTags.add("video");
        tags.put(domainYoutube, youtubeTags);
        tags.put(domainYoutubeShort, youtubeTags);

        final String domainAcm = "dl.acm.org";
        final ArrayList<String> acmTags = new ArrayList<>();
        acmTags.add("article");
        tags.put(domainAcm, acmTags);

        final String domainIeee = "ieeexplore.ieee.org";
        final ArrayList<String> ieeeTags = new ArrayList<>();
        ieeeTags.add("article");
        tags.put(domainIeee, ieeeTags);
    }

    public List<String> find(String url) {
        for (String domain : tags.keySet()) {
            if (url.contains(domain)) {
                return tags.get(domain);
            }
        }
        return new ArrayList<>();
    }


}
