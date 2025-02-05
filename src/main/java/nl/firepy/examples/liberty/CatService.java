package nl.firepy.examples.liberty;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CatService {
    private List<CatDto> cats = List.of(
        new CatDto("Bob", FurColor.BLACK, 8),
        new CatDto("Timmy", FurColor.BLACK, 8),
        new CatDto("Liss", FurColor.WHITE, 8),
        new CatDto("Boris", FurColor.BROWN, 8)
    );

    public List<CatDto> getCats() {
        return cats;
    }

    public Optional<CatDto> findByName(String name) {
        return cats.stream().filter(cat -> cat.getName().equals(name)).findFirst();
    }

    public void k() {
        Boolean b = false;
        if (b) {
            
        }
    }
}
