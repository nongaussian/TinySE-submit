package edu.hanyang;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.hanyang.indexer.DocumentCursor;
import edu.hanyang.submit.TinySEQueryProcess;
import edu.hanyang.utils.TestDocCursor;
import edu.hanyang.utils.TestIntermediateList;
import edu.hanyang.utils.TestIntermediatePositionalList;

public class QueryProcessTest {
	List<List<Integer>> posList;
	TinySEQueryProcess qp;

	@Before
	public void init() {
		posList = makePostingList();
		qp = new TinySEQueryProcess();
	}

	@Test
	public void test_op_and_wo_pos() throws IOException {
		TestDocCursor tdc1 = new TestDocCursor(posList.get(0));
		TestDocCursor tdc2 = new TestDocCursor(posList.get(1));
		TestIntermediateList out = new TestIntermediateList();

		qp.op_and_wo_pos(tdc1, tdc2, out);
		DocumentCursor dc = new TestDocCursor(out);
		assertEquals(dc.get_docid(), 0);
		dc.go_next();
		assertEquals(dc.get_docid(), 1);
		dc.go_next();
		assertEquals(dc.get_docid(), 2);
		dc.go_next();
		assertEquals(dc.get_docid(), 3);
		dc.go_next();
		assertEquals(dc.get_docid(), 4);
		dc.go_next();
		assertEquals(dc.is_eol(), true);
		
		
		tdc1 = new TestDocCursor(posList.get(3));
		tdc2 = new TestDocCursor(posList.get(6));
		out = new TestIntermediateList();

		qp.op_and_wo_pos(tdc1, tdc2, out);
		dc = new TestDocCursor(out);
		assertEquals(dc.get_docid(), 0);
		dc.go_next();
		assertEquals(dc.get_docid(), 1);
		dc.go_next();
		assertEquals(dc.get_docid(), 4);
		dc.go_next();
		assertEquals(dc.is_eol(), true);
	}

	@Test
	public void test_op_and_w_pos() throws IOException {
		TestDocCursor tdc1 = new TestDocCursor(posList.get(3));
		TestDocCursor tdc2 = new TestDocCursor(posList.get(4));
		TestIntermediatePositionalList out = new TestIntermediatePositionalList();

		qp.op_and_w_pos(tdc1, tdc2, 1, out);
		DocumentCursor dc = new TestDocCursor(out);
		
		assertEquals(dc.get_docid(), 1);
		dc.go_next();
		assertEquals(dc.get_docid(), 2);
		dc.go_next();
		assertEquals(dc.get_docid(), 4);
		dc.go_next();
		assertEquals(dc.is_eol(), true);
		
		
		tdc1 = new TestDocCursor(posList.get(2));
		tdc2 = new TestDocCursor(posList.get(4));
		out = new TestIntermediatePositionalList();

		qp.op_and_w_pos(tdc1, tdc2, 1, out);
		dc = new TestDocCursor(out);

		assertEquals(dc.get_docid(), 1);
		dc.go_next();
		assertEquals(dc.get_docid(), 4);
		dc.go_next();
		assertEquals(dc.is_eol(), true);
	}

	private List<List<Integer>> makePostingList() {
		List<List<Integer>> posList = new ArrayList<List<Integer>>();
		posList.add(Arrays.asList(0, 3, 0, 9, 13, 1, 2, 4, 17, 2, 1, 13, 3, 2, 0, 18, 4, 1, 15));
		posList.add(Arrays.asList(0, 1, 16, 1, 2, 9, 14, 2, 1, 2, 3, 4, 6, 11, 17, 22, 4, 2, 4, 14));
		posList.add(Arrays.asList(0, 2, 2, 7, 1, 5, 1, 5, 12, 15, 18, 2, 3, 1, 7, 8, 3, 5, 5, 10, 15, 19, 20, 4, 3, 3,
				9, 16));
		posList.add(Arrays.asList(0, 1, 19, 1, 4, 0, 8, 13, 19, 2, 2, 4, 9, 4, 2, 2, 10));
		posList.add(Arrays.asList(0, 1, 18, 1, 3, 2, 7, 20, 2, 2, 3, 5, 3, 3, 2, 12, 23, 4, 4, 1, 6, 11, 17));
		posList.add(Arrays.asList(0, 2, 4, 14, 1, 1, 16, 2, 2, 6, 12, 3, 1, 8, 4, 1, 7));
		posList.add(Arrays.asList(0, 1, 10, 1, 2, 10, 22, 3, 3, 1, 4, 16, 4, 2, 5, 12));
		posList.add(Arrays.asList(0, 1, 3, 1, 1, 6, 2, 2, 0, 11, 3, 3, 7, 9, 14, 4, 2, 13, 18));
		posList.add(Arrays.asList(0, 3, 5, 8, 11, 1, 1, 3, 2, 2, 10, 15, 3, 1, 13, 4, 1, 0));
		posList.add(Arrays.asList(0, 5, 1, 6, 12, 15, 17, 1, 2, 11, 21, 2, 1, 14, 3, 2, 3, 21, 4, 1, 8));

		return posList;
	}
}
