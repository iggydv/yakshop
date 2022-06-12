package com.xebia.yakshop.models.storage

import com.xebia.yakshop.models.LabYakInternal
import com.xebia.yakshop.models.SexInternal
import com.xebia.yakshop.storage.HerdStorageImpl
import spock.lang.Specification
import spock.lang.Subject

class HerdStorageImplTest extends Specification {

    private final List<LabYakInternal> herd = List.of(
            LabYakInternal.builder().age(4.0).name("Betty-1").sex(SexInternal.F).build(),
            LabYakInternal.builder().age(8.0).name("Betty-2").sex(SexInternal.F).build(),
            LabYakInternal.builder().age(9.5).name("Betty-3").sex(SexInternal.F).build())

    @Subject
    private final HerdStorageImpl herdInternal = new HerdStorageImpl()

    def "should set the herd"() {
        when:
        herdInternal.setHerd(herd)

        then:
        assert herdInternal.getHerd().size() == 3
    }
}
