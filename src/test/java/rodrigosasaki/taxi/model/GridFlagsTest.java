package rodrigosasaki.taxi.model;

import com.rodrigosasaki.taxi.service.Grid;
import com.rodrigosasaki.taxi.model.GridFlags;
import com.rodrigosasaki.taxi.parser.CSVParser;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GridFlagsTest {

    @Test
    public void testIdentifiesBlocksCorrectly(){
        Grid grid = new Grid(CSVParser.parseCSV("x,\n,x"));
        GridFlags flags = new GridFlags(grid);

        assertTrue(flags.blocked(0, 0));
        assertFalse(flags.blocked(0, 1));
        assertFalse(flags.blocked(1, 0));
        assertTrue(flags.blocked(1, 1));
    }

}
