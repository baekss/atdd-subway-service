package nextstep.subway.member.application;

import nextstep.subway.member.domain.Favorite;
import nextstep.subway.member.domain.Member;
import nextstep.subway.member.domain.MemberRepository;
import nextstep.subway.member.dto.FavoriteResponse;
import nextstep.subway.member.dto.MemberRequest;
import nextstep.subway.member.dto.MemberResponse;
import nextstep.subway.station.domain.Station;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse createMember(MemberRequest request) {
        Member member = memberRepository.save(request.toMember());
        return MemberResponse.of(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(RuntimeException::new);
        return MemberResponse.of(member);
    }

    public void updateMember(Long id, MemberRequest param) {
        Member member = memberRepository.findById(id).orElseThrow(RuntimeException::new);
        member.update(param.toMember());
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Favorite createFavorite(Long id, Station source, Station target) {
        Member member = memberRepository.findById(id).orElseThrow(RuntimeException::new);
        return new Favorite(member, source, target);
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponse> findFavorites(Long id) {
        Member member = memberRepository.findByMemberId(id);
        return FavoriteResponse.ofList(member.getFavorites());
    }

    public void removeFavorite(Long id, Long favoriteId) {
        Member member = memberRepository.findByMemberId(id);
        member.getFavorites().removeIf(favorite -> favorite.getId().equals(favoriteId));
    }
}
