import {shallowMount} from "@vue/test-utils";

import {expect} from 'chai';
import HelloWorld from "../../src/components/HelloWorld";

describe('HelloWorldVue-test', function () {

  let wrapper;
  beforeEach(() => {
    wrapper = shallowMount(HelloWorld, {});
  })

  it('renders props.msg when passed', () => {
    expect(wrapper.vm.$el.querySelectorAll("[type=button]").length).to.equal(3)
  });

  it('init rendering visit test', done => {
    const url = "qqq";
    const eventSourceInitDict = {headers: {'Cookie': 'test=test'}};
    const es = new EventSource(`http://psawesome.org:8080/api/v1/transformed/${url}`, eventSourceInitDict);
    es.onmessage = (e) => {
      expect(e.data).exist.equal("");
      done()
    }
    es.onerror = (e) => {
      console.log(e)
    }
  });
});