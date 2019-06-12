package lotto.domain;

import lotto.dto.LottoDto;
import lotto.dao.LottosDao;
import lotto.dao.TurnDao;

import java.util.ArrayList;
import java.util.List;

// TODO 여기안에서 수동 번호, 자동 번호를 계산한다
// domain에서 분리한다
// domain 객체들을 호출 사용한다.
// 일단 main에서 service만 쓰게한다
public class LottoService {
    private final LottoMachine lottoMachine;
    private final LottosDao lottosDao;
    private final TurnDao turnDao;

    public LottoService() {
        lottoMachine = new LottoMachine();
        lottosDao = LottosDao.getInstance();
        turnDao = TurnDao.getInstance();
    }

    public void charge(final int money) {
        lottoMachine.charge(money);
    }

    public void buy(final Lotto lotto) {
        lottoMachine.buy();
        lottosDao.add(LottoDto.of(lotto), turnDao.findNext());
    }

    public boolean canBuy() {
        return lottoMachine.isRemainMoney();
    }

    public GameResultMatcher gameResult() {
        List<Lotto> lottos = new ArrayList<>();
        for (LottoDto lotto : getLottos()) {
            lottos.add(lotto.lottoValue());
        }
        return GameResultMatcher.of(lottos);
    }

    public List<LottoDto> getLottos() {
        return lottosDao.findAllByTurn(turnDao.findNext());
    }

    public void vacateMoney() {
        lottoMachine.vacate();
    }

    public void deleteAll() {
        lottosDao.deleteAll();
    }

    public List<LottoDto> findAllByTurn(final int turn) {
        return lottosDao.findAllByTurn(turn);
    }
}
